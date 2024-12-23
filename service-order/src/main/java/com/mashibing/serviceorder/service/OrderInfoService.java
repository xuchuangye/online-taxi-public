package com.mashibing.serviceorder.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mashibing.internalcommon.constant.CommonStatusEnum;
import com.mashibing.internalcommon.constant.IdentityConstant;
import com.mashibing.internalcommon.constant.OrderConstant;
import com.mashibing.internalcommon.dto.Car;
import com.mashibing.internalcommon.dto.OrderInfo;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.CalculatePriceRequest;
import com.mashibing.internalcommon.request.OrderRequest;
import com.mashibing.internalcommon.response.OrderAboutDriverResponse;
import com.mashibing.internalcommon.response.TerminalResponse;
import com.mashibing.internalcommon.response.TrsearchTerminalResponse;
import com.mashibing.internalcommon.utils.RedisKeyUtils;
import com.mashibing.serviceorder.mapper.OrderInfoMapper;
import com.mashibing.serviceorder.mapper.ServiceSsePushClient;
import com.mashibing.serviceorder.remote.ServiceDriverUserClient;
import com.mashibing.serviceorder.remote.ServiceMapClient;
import com.mashibing.serviceorder.remote.ServicePriceClient;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author xcy
 * @date 2023/2/20 - 7:55
 */
@Service
@Slf4j
public class OrderInfoService {

    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private ServicePriceClient servicePriceClient;

    @Autowired
    private ServiceDriverUserClient serviceDriverUserClient;

    @Autowired
    private RedissonClient redissonClient;

    /**
     * 乘客下单，创建订单
     *
     * @param orderRequest
     * @return
     */
    public ResponseResult addOrder(OrderRequest orderRequest) {
        //获取最新版本的计价规则
		/*ResponseResult<PriceRule> priceRuleResponseResult = servicePriceClient.getNewestVersion(orderRequest.getFareType());
		PriceRule priceRule = priceRuleResponseResult.getData();
		if (priceRule == null) {
			return ResponseResult.fail(CommonStatusEnum.PRICE_RULE_NOT_EXISTS.getCode(),
					CommonStatusEnum.PRICE_RULE_NOT_EXISTS.getMessage());
		}
		Integer fareVersion = priceRule.getFareVersion();*/

        //根据城市编码查询当前城市是否有可用司机
        String cityCode = orderRequest.getAddress();
        ResponseResult<Boolean> isAvailableDriver = serviceDriverUserClient.isAvailableDriver(cityCode);

        if (isAvailableDriver == null || !isAvailableDriver.getData()) {
            return ResponseResult.fail(CommonStatusEnum.CITY_NOT_IS_AVAILABLE_DRIVER.getCode(),
                    CommonStatusEnum.CITY_NOT_IS_AVAILABLE_DRIVER.getMessage());
        }
        log.info("根据城市编码查询当前城市是否有可用司机的代码测试成功" + isAvailableDriver.getData());


        //判断当前版本是否是最新的
        ResponseResult<Boolean> newestVersionResponseResult = servicePriceClient.isNewestVersion(orderRequest.getFareType(), orderRequest.getFareVersion());
        Boolean isNewestVersion = newestVersionResponseResult.getData();
        if (isNewestVersion == null || !isNewestVersion) {
            return ResponseResult.fail(CommonStatusEnum.PRICE_RULE_NOT_NEWEST.getCode(),
                    CommonStatusEnum.PRICE_RULE_NOT_NEWEST.getMessage()
                    /*,"最新的版本是：" + fareVersion*/);
        }
        //判断当前设备是否是黑名单设备
        //1.获取deviceCode
        String deviceCode = orderRequest.getDeviceCode();
        //2.生成存储到Redis中的key
        String deviceCodeKey = RedisKeyUtils.deviceCodePrefix + deviceCode;
        //3.查看Redis中是否有deviceCodeKey
        if (isBlackDevice(deviceCodeKey)) {
            return ResponseResult.fail(CommonStatusEnum.DEVICE_LOGIN_EXCEPTION.getCode(),
                    CommonStatusEnum.DEVICE_LOGIN_EXCEPTION.getMessage());
        }

        //判断当前城市编码和车辆类型的计价规则是否存在
        if (!isExistsPriceRule(orderRequest)) {
            return ResponseResult.fail(CommonStatusEnum.CITY_NOT_PROVIDE_SERVICE.getCode(),
                    CommonStatusEnum.CITY_NOT_PROVIDE_SERVICE.getMessage());
        }

        //判断乘客是否有正在进行中的订单
        int count = isPassengerOrderGoingon(orderRequest.getPassengerId());
        if (count > 0) {
            return ResponseResult.fail(CommonStatusEnum.ORDER_IN_PROGRESS.getCode()
                    , CommonStatusEnum.ORDER_IN_PROGRESS.getMessage());
        }

        OrderInfo orderInfo = new OrderInfo();
        BeanUtils.copyProperties(orderRequest, orderInfo);

        //订单开始
        orderInfo.setOrderStatus(OrderConstant.ORDER_START);
        //设计设置创建时间
        LocalDateTime now = LocalDateTime.now();
        orderInfo.setGmtCreate(now);
        orderInfo.setGmtModified(now);

        orderInfoMapper.insert(orderInfo);

        //定时任务，轮询查找附近可用司机
        for (int i = 0; i < 6; i++) {
            //实时订单派单逻辑
            int result = dispatchRealTimeOrder(orderInfo);
            if (result == 1) {
                break;
            }

            if (i == 5) {
                orderInfo.setOrderStatus(OrderConstant.ORDER_INVALID);
                orderInfoMapper.updateById(orderInfo);
            } else {
                try {
                    Thread.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
        return ResponseResult.success("");
    }

    /**
     * 乘客下订预约单，司机进行抢单
     *
     * @param orderRequest
     * @return
     */
    public ResponseResult bookOrder(OrderRequest orderRequest) {
        //获取最新版本的计价规则
		/*ResponseResult<PriceRule> priceRuleResponseResult = servicePriceClient.getNewestVersion(orderRequest.getFareType());
		PriceRule priceRule = priceRuleResponseResult.getData();
		if (priceRule == null) {
			return ResponseResult.fail(CommonStatusEnum.PRICE_RULE_NOT_EXISTS.getCode(),
					CommonStatusEnum.PRICE_RULE_NOT_EXISTS.getMessage());
		}
		Integer fareVersion = priceRule.getFareVersion();*/

        //根据城市编码查询当前城市是否有可用司机
		/* String cityCode = orderRequest.getAddress();
		ResponseResult<Boolean> isAvailableDriver = serviceDriverUserClient.isAvailableDriver(cityCode);

		if (isAvailableDriver == null || !isAvailableDriver.getData()) {
			return ResponseResult.fail(CommonStatusEnum.CITY_NOT_IS_AVAILABLE_DRIVER.getCode(),
					CommonStatusEnum.CITY_NOT_IS_AVAILABLE_DRIVER.getMessage());
		}
		log.info("根据城市编码查询当前城市是否有可用司机的代码测试成功" + isAvailableDriver.getData()); */


        //判断当前计价规则的版本是否是最新的
		/* ResponseResult<Boolean> newestVersionResponseResult = servicePriceClient.isNewestVersion(orderRequest.getFareType(), orderRequest.getFareVersion());
		Boolean isNewestVersion = newestVersionResponseResult.getData();
		if (isNewestVersion == null || !isNewestVersion) {
			return ResponseResult.fail(CommonStatusEnum.PRICE_RULE_NOT_NEWEST.getCode(),
					CommonStatusEnum.PRICE_RULE_NOT_NEWEST.getMessage()
					 *//*,"最新的版本是：" + fareVersion*//* );
		} */

        //判断当前设备是否是黑名单设备
		/* //1.获取deviceCode
		String deviceCode = orderRequest.getDeviceCode();
		//2.生成存储到Redis中的key
		String deviceCodeKey = RedisKeyUtils.deviceCodePrefix + deviceCode;
		//3.查看Redis中是否有deviceCodeKey
		if (isBlackDevice(deviceCodeKey)) {
			return ResponseResult.fail(CommonStatusEnum.DEVICE_LOGIN_EXCEPTION.getCode(),
					CommonStatusEnum.DEVICE_LOGIN_EXCEPTION.getMessage());
		} */

        //判断当前城市编码和车辆类型的计价规则是否存在
		/* if (!isExistsPriceRule(orderRequest)) {
			return ResponseResult.fail(CommonStatusEnum.CITY_NOT_PROVIDE_SERVICE.getCode(),
					CommonStatusEnum.CITY_NOT_PROVIDE_SERVICE.getMessage());
		} */

        //判断乘客是否有正在进行中的订单
		/* int count = isPassengerOrderGoingon(orderRequest.getPassengerId());
		if (count > 0) {
			return ResponseResult.fail(CommonStatusEnum.ORDER_IN_PROGRESS.getCode()
					, CommonStatusEnum.ORDER_IN_PROGRESS.getMessage());
		} */

        //创建订单，找到附近的司机，然后将订单推送周边的司机
        OrderInfo orderInfo = new OrderInfo();
        BeanUtils.copyProperties(orderRequest, orderInfo);

        //订单开始
        orderInfo.setOrderStatus(OrderConstant.ORDER_START);
        //设计设置创建时间
        LocalDateTime now = LocalDateTime.now();
        orderInfo.setGmtCreate(now);
        orderInfo.setGmtModified(now);

        //创建订单
        orderInfoMapper.insert(orderInfo);

        //定时任务，轮询查找附近可用司机
        for (int i = 0; i < 6; i++) {
            //实时订单派单逻辑
            int result = dispatchBookOrder(orderInfo);
            if (result == 1) {
                break;
            }

            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        return ResponseResult.success("");
    }

    @Autowired
    private ServiceMapClient serviceMapClient;

    @Autowired
    private ServiceSsePushClient serviceSsePushClient;

    /**
     * 实时订单派单逻辑
     *
     * @param orderInfo
     */
    public int dispatchRealTimeOrder(OrderInfo orderInfo) {
        log.info("循环一次");
        int result = 0;
        //出发地纬度
        String depLatitude = orderInfo.getDepLatitude();
        //出发地经度
        String depLongitude = orderInfo.getDepLongitude();
        String center = depLatitude + "," + depLongitude;

        List<Integer> radiusLists = new ArrayList<>();
        radiusLists.add(2000);
        radiusLists.add(4000);
        radiusLists.add(5000);
        ResponseResult<List<TerminalResponse>> listResponseResult = null;

        radius:
        for (int i = 0; i < radiusLists.size(); i++) {
            int radius = radiusLists.get(i);
            listResponseResult = serviceMapClient.terminalAroundSearch(center, radius);
            log.info("此次派单的终端周边搜索在" + radius + "KM范围内进行搜索，搜索的结果是："
                    + listResponseResult.getData().toString());
            //查询终端

            //解析终端，carId: 1627234296248999938, tid:637818785
            //根据解析终端获取车辆信息
            //1.司机是出车状态
            //2.司机没有正在进行的订单
            List<TerminalResponse> data = listResponseResult.getData();
            //List<TerminalResponse> data = new ArrayList<>();
            for (int j = 0; j < data.size(); j++) {
                TerminalResponse terminalResponse = data.get(i);
                Long carId = terminalResponse.getDesc();
                //获取经度
                String longitude = terminalResponse.getLongitude();
                //获取经度
                String latitude = terminalResponse.getLatitude();

                ResponseResult<OrderAboutDriverResponse> availableDriver = serviceDriverUserClient.getAvailableDriver(carId);
                if (availableDriver.getCode() == CommonStatusEnum.NOT_AVAILABLE_DRIVER.getCode()) {
                    log.info("没有车辆" + carId + "可以派单的司机");
                    continue;
                } else {
                    log.info("车辆" + carId + "有可以派单的司机");
                    //获取订单中关于司机的信息
                    OrderAboutDriverResponse orderAboutDriverResponse = availableDriver.getData();
                    Long driverId = orderAboutDriverResponse.getDriverId();
                    String driverPhone = orderAboutDriverResponse.getDriverPhone();
                    String licenseId = orderAboutDriverResponse.getLicenseId();
                    String vehicleNo = orderAboutDriverResponse.getVehicleNo();
                    String vehicleTypeFromCar = orderAboutDriverResponse.getVehicleType();

                    String vehicleType = orderInfo.getVehicleType();
                    //判断车辆类型是否匹配
                    if (!vehicleType.trim().equals(vehicleTypeFromCar.trim())) {
                        log.info("没有车辆可以进行匹配");
                        continue;
                    }


                    String lockKey = (driverId + "").intern();
                    RLock lock = redissonClient.getLock(lockKey);
                    lock.lock();

                    //synchronized (OrderInfoService.class) {
                    //synchronized ((driverId + "").intern()) {
                    //判断司机是否有正在进行的订单
                    //如果司机有正在进行的订单，那么继续下一次循环查找司机
                    if (isDriverOrderGoingon(driverId) > 0) {
                        lock.unlock();
                        continue;
                    }
                    //如果司机没有正在进行的订单，表示该司机可以出车派遣，证明找到了合适的司机

                    //订单直接匹配司机
                    //查询当前司机信息
                    QueryWrapper<Car> carQueryWrapper = new QueryWrapper<>();
                    carQueryWrapper.eq("car_id", carId);

                    //查询当前车辆信息
                    orderInfo.setDriverId(driverId);
                    orderInfo.setDriverPhone(driverPhone);
                    orderInfo.setCarId(carId);

                    //
                    orderInfo.setReceiveOrderTime(LocalDateTime.now());
                    orderInfo.setReceiveOrderCarLongitude(longitude);
                    orderInfo.setReceiveOrderCarLatitude(latitude);

                    orderInfo.setLicenseId(licenseId);
                    orderInfo.setVehicleNo(vehicleNo);
                    orderInfo.setOrderStatus(OrderConstant.DRIVER_RECEIVE_ORDER);

                    orderInfoMapper.updateById(orderInfo);

                    //向司机客户端推送消息
                    JSONObject driverContent = new JSONObject();
                    driverContent.put("orderId", orderInfo.getId());
                    driverContent.put("passengerId", orderInfo.getPassengerId());
                    driverContent.put("passengerPhone", orderInfo.getPassengerPhone());
                    driverContent.put("departTime", orderInfo.getDepartTime());
                    driverContent.put("depLongitude", orderInfo.getDepLongitude());
                    driverContent.put("depLatitude", orderInfo.getDepLatitude());
                    driverContent.put("destination", orderInfo.getDestination());
                    driverContent.put("destLongitude", orderInfo.getDestLongitude());
                    driverContent.put("destLatitude", orderInfo.getDestLatitude());

                    serviceSsePushClient.push(driverId, IdentityConstant.DRIVER_IDENTITY, driverContent.toString());


                    //向乘客客户端推送消息
                    JSONObject passengerContent = new JSONObject();
                    //司机信息
                    passengerContent.put("orderId", orderInfo.getId());
                    passengerContent.put("driverId", driverId);
                    passengerContent.put("driverPhone", orderInfo.getDriverPhone());
                    ResponseResult<Car> carResponseResult = serviceDriverUserClient.getCar(carId);
                    Car car = carResponseResult.getData();
                    //车辆信息
                    passengerContent.put("vehicleNo", car.getVehicleNo());
                    passengerContent.put("brand", car.getBrand());
                    passengerContent.put("model", car.getModel());
                    passengerContent.put("vehicleColor", car.getVehicleColor());
                    //司机接单信息
                    passengerContent.put("receiveOrderCarLongitude", orderInfo.getReceiveOrderCarLongitude());
                    passengerContent.put("receiveOrderCarLatitude", orderInfo.getReceiveOrderCarLatitude());

                    serviceSsePushClient.push(orderInfo.getPassengerId(), IdentityConstant.PASSENGER_IDENTITY, passengerContent.toString());

                    result = 1;

                    lock.unlock();

                    break radius;
                    //}
                }
            }

            //找到符合的车辆，进行派单

            //如果派单成功，则退出循环
        }
        return result;
    }

    /**
     * 预约订单派单逻辑
     *
     * @param orderInfo
     */
    public int dispatchBookOrder(OrderInfo orderInfo) {
        log.info("循环一次");
        int result = 0;
        //出发地纬度
        String depLatitude = orderInfo.getDepLatitude();
        //出发地经度
        String depLongitude = orderInfo.getDepLongitude();
        String center = depLatitude + "," + depLongitude;

        List<Integer> radiusLists = new ArrayList<>();
        radiusLists.add(2000);
        radiusLists.add(4000);
        radiusLists.add(5000);
        ResponseResult<List<TerminalResponse>> listResponseResult = null;

        radius:
        for (int i = 0; i < radiusLists.size(); i++) {
            int radius = radiusLists.get(i);
            listResponseResult = serviceMapClient.terminalAroundSearch(center, radius);
            log.info("此次派单的终端周边搜索在" + radius + "KM范围内进行搜索，搜索的结果是："
                    + listResponseResult.getData().toString());
            //查询终端

            //解析终端，carId: 1627234296248999938, tid:637818785
            //根据解析终端获取车辆信息
            //1.司机是出车状态
            //2.司机没有正在进行的订单
            //真实数据
            //List<TerminalResponse> data = listResponseResult.getData();
            //测试数据
            TerminalResponse testTerminal = new TerminalResponse();
            testTerminal.setCarId(1868576644493398018L);
            testTerminal.setTid("1112624258");
            testTerminal.setLongitude("39.908308");
            testTerminal.setLatitude("116.421289");
            List<TerminalResponse> data = new ArrayList<>();
            data.add(testTerminal);
            //List<TerminalResponse> data = new ArrayList<>();
            for (int j = 0; j < data.size(); j++) {
                TerminalResponse terminalResponse = data.get(i);
                Long carId = terminalResponse.getCarId();
                //获取经度
                String longitude = terminalResponse.getLongitude();
                //获取经度
                String latitude = terminalResponse.getLatitude();

                ResponseResult<OrderAboutDriverResponse> availableDriver = serviceDriverUserClient.getAvailableDriver(carId);
                if (availableDriver.getCode() == CommonStatusEnum.NOT_AVAILABLE_DRIVER.getCode()) {
                    log.info("没有车辆ID: " + carId + "可以派单的司机");
                    continue;
                } else {
                    log.info("车辆ID: " + carId + "检车到正在出车可以派单的司机");
                    //获取订单中关于司机的信息
                    OrderAboutDriverResponse orderAboutDriverResponse = availableDriver.getData();
                    Long driverId = orderAboutDriverResponse.getDriverId();
                    String driverPhone = orderAboutDriverResponse.getDriverPhone();
                    String licenseId = orderAboutDriverResponse.getLicenseId();
                    String vehicleNo = orderAboutDriverResponse.getVehicleNo();
                    String vehicleTypeFromCar = orderAboutDriverResponse.getVehicleType();

                    String vehicleType = orderInfo.getVehicleType();
                    //判断车辆类型是否匹配
                    if (!vehicleType.trim().equals(vehicleTypeFromCar.trim())) {
                        log.info("没有车辆可以进行匹配");
                        continue;
                    }


					/* String lockKey = (driverId + "").intern();
					RLock lock = redissonClient.getLock(lockKey);
					lock.lock();

					//synchronized (OrderInfoService.class) {
					//synchronized ((driverId + "").intern()) {
					//判断司机是否有正在进行的订单
					//如果司机有正在进行的订单，那么继续下一次循环查找司机
					if (isDriverOrderGoingon(driverId) > 0) {
						lock.unlock();
						continue;
					}
					//如果司机没有正在进行的订单，表示该司机可以出车派遣，证明找到了合适的司机

					//订单直接匹配司机
					//查询当前司机信息
					QueryWrapper<Car> carQueryWrapper = new QueryWrapper<>();
					carQueryWrapper.eq("car_id", carId);

					//查询当前车辆信息
					orderInfo.setDriverId(driverId);
					orderInfo.setDriverPhone(driverPhone);
					orderInfo.setCarId(carId);

					//从地图中来
					orderInfo.setReceiveOrderTime(LocalDateTime.now());
					orderInfo.setReceiveOrderCarLongitude(longitude);
					orderInfo.setReceiveOrderCarLatitude(latitude);

					orderInfo.setLicenseId(licenseId);
					orderInfo.setVehicleNo(vehicleNo);
					orderInfo.setOrderStatus(OrderConstant.DRIVER_RECEIVE_ORDER);

					orderInfoMapper.updateById(orderInfo); */

                    //向司机客户端推送消息
                    JSONObject driverContent = new JSONObject();
                    driverContent.put("orderId", orderInfo.getId());
                    driverContent.put("passengerId", orderInfo.getPassengerId());
                    driverContent.put("passengerPhone", orderInfo.getPassengerPhone());
                    driverContent.put("departTime", orderInfo.getDepartTime());
                    driverContent.put("depLongitude", orderInfo.getDepLongitude());
                    driverContent.put("depLatitude", orderInfo.getDepLatitude());
                    driverContent.put("destination", orderInfo.getDestination());
                    driverContent.put("destLongitude", orderInfo.getDestLongitude());
                    driverContent.put("destLatitude", orderInfo.getDestLatitude());

                    serviceSsePushClient.push(driverId, IdentityConstant.DRIVER_IDENTITY, driverContent.toString());


					/* //向乘客客户端推送消息
					JSONObject passengerContent = new JSONObject();
					//司机信息
					passengerContent.put("orderId", orderInfo.getId());
					passengerContent.put("driverId", driverId);
					passengerContent.put("driverPhone", orderInfo.getDriverPhone());
					ResponseResult<Car> carResponseResult = serviceDriverUserClient.getCar(carId);
					Car car = carResponseResult.getData();
					//车辆信息
					passengerContent.put("vehicleNo", car.getVehicleNo());
					passengerContent.put("brand", car.getBrand());
					passengerContent.put("model", car.getModel());
					passengerContent.put("vehicleColor", car.getVehicleColor());
					//司机接单信息
					passengerContent.put("receiveOrderCarLongitude", orderInfo.getReceiveOrderCarLongitude());
					passengerContent.put("receiveOrderCarLatitude", orderInfo.getReceiveOrderCarLatitude());

					serviceSsePushClient.push(orderInfo.getPassengerId(), IdentityConstant.PASSENGER_IDENTITY, passengerContent.toString());

					result = 1;

					lock.unlock(); */

                    result = 1;

                    break radius;
                    //}
                }
            }

            //找到符合的车辆，进行派单

            //如果派单成功，则退出循环
        }
        return result;
    }

    /**
     * 判断当前城市编码和车辆类型的计价规则是否存在
     *
     * @param orderRequest
     * @return
     */
    private boolean isExistsPriceRule(OrderRequest orderRequest) {
        String fareType = orderRequest.getFareType();
        String[] split = fareType.split("\\$");
        String cityCode = split[0];
        String vehicleType = split[1];
        ResponseResult<Boolean> exists = servicePriceClient.isExists(cityCode, vehicleType);
        return exists.getData();
    }

    /**
     * 判断设备是否登录异常，或者登录设备是黑名单用户
     *
     * @param deviceCodeKey
     * @return
     */
    private boolean isBlackDevice(String deviceCodeKey) {
        Boolean isHasKey = stringRedisTemplate.hasKey(deviceCodeKey);
        //如果Redis中存在deviceCodeKey
        if (isHasKey == null || isHasKey) {
            //获取value值：次数
            int count = Integer.parseInt(Objects.requireNonNull(stringRedisTemplate.opsForValue().get(deviceCodeKey)));
            //如果value值：次数，已经超过1次，提示设备登录异常
            if (count >= 2) {
                return true;
            }
            //如果value值：次数，没有超过1次，则进行累加
            stringRedisTemplate.opsForValue().increment(deviceCodeKey);
        }
        //如果Redis中不存在deviceCodeKey，则直接添加key，设置value和过期时间
        else {
            stringRedisTemplate.opsForValue().set(deviceCodeKey, "1", 1, TimeUnit.HOURS);
        }
        return false;
    }

    /**
     * 判断司机正在进行的订单
     *
     * @param driverId
     * @return
     */
    private int isDriverOrderGoingon(Long driverId) {
        QueryWrapper<OrderInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("driver_id", driverId);
        queryWrapper.and(wrapper -> wrapper.eq("order_status", OrderConstant.DRIVER_RECEIVE_ORDER)
                .or().eq("order_status", OrderConstant.DRIVER_TO_PICK_UP_PASSENGER)
                .or().eq("order_status", OrderConstant.DRIVER_ARRIVED_DEPARTURE)
                .or().eq("order_status", OrderConstant.DRIVER_PICK_UP_PASSENGER)
        );
        Integer availableDriverCount = orderInfoMapper.selectCount(queryWrapper);
        log.info("司机id" + driverId + "，正在接单的数量：" + availableDriverCount);
        return availableDriverCount;
    }

    /**
     * 判断乘客正在进行的订单
     *
     * @param passengerId
     * @return
     */
    private int isPassengerOrderGoingon(Long passengerId) {
        QueryWrapper<OrderInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("passenger_id", passengerId);
        queryWrapper.and(wrapper -> wrapper.eq("order_status", OrderConstant.ORDER_START)
                .or().eq("order_status", OrderConstant.DRIVER_RECEIVE_ORDER)
                .or().eq("order_status", OrderConstant.DRIVER_TO_PICK_UP_PASSENGER)
                .or().eq("order_status", OrderConstant.DRIVER_ARRIVED_DEPARTURE)
                .or().eq("order_status", OrderConstant.DRIVER_PICK_UP_PASSENGER)
                .or().eq("order_status", OrderConstant.PASSENGER_GET_OFF)
                .or().eq("order_status", OrderConstant.INITIATE_COLLECTIONS)
        );

        return orderInfoMapper.selectCount(queryWrapper);
    }


    /**
     * 司机去接乘客
     *
     * @param orderRequest
     * @return
     */
    public ResponseResult toPickUpPassenger(OrderRequest orderRequest) {
        //获取订单id
        Long orderId = orderRequest.getOrderId();
        //司机去接乘客的经度
        String toPickUpPassengerLongitude = orderRequest.getToPickUpPassengerLongitude();
        //司机去接乘客的维度
        String toPickUpPassengerLatitude = orderRequest.getToPickUpPassengerLatitude();
        //司机去接乘客的地址
        String toPickUpPassengerAddress = orderRequest.getToPickUpPassengerAddress();

        QueryWrapper<OrderInfo> orderInfoQueryWrapper = new QueryWrapper<>();
        orderInfoQueryWrapper.eq("id", orderId);
        OrderInfo orderInfo = orderInfoMapper.selectOne(orderInfoQueryWrapper);

        //司机去接乘客的时间
        orderInfo.setToPickUpPassengerTime(LocalDateTime.now());
        orderInfo.setToPickUpPassengerLongitude(toPickUpPassengerLongitude);
        orderInfo.setToPickUpPassengerLatitude(toPickUpPassengerLatitude);
        orderInfo.setToPickUpPassengerAddress(toPickUpPassengerAddress);

        //设置订单的状态为：司机去接乘客
        orderInfo.setOrderStatus(OrderConstant.DRIVER_TO_PICK_UP_PASSENGER);

        orderInfoMapper.updateById(orderInfo);

        return ResponseResult.success("");
    }

    /**
     * 司机到达乘客出发点
     *
     * @param orderRequest
     * @return
     */
    public ResponseResult driverArrivedDeparture(OrderRequest orderRequest) {
        Long orderId = orderRequest.getOrderId();

        QueryWrapper<OrderInfo> orderInfoQueryWrapper = new QueryWrapper<>();
        orderInfoQueryWrapper.eq("id", orderId);
        OrderInfo orderInfo = orderInfoMapper.selectOne(orderInfoQueryWrapper);

        //设置司机到达乘客出发点的时间
        orderInfo.setDriverArrivedDepartureTime(LocalDateTime.now());
        //设置订单状态：司机到达乘客出发点
        orderInfo.setOrderStatus(OrderConstant.DRIVER_ARRIVED_DEPARTURE);

        orderInfoMapper.updateById(orderInfo);

        return ResponseResult.success("");
    }

    /**
     * 司机接到乘客
     *
     * @param orderRequest
     * @return
     */
    public ResponseResult pickUpPassenger(OrderRequest orderRequest) {
        Long orderId = orderRequest.getOrderId();
        //司机接到乘客的经度和维度
        String pickUpPassengerLongitude = orderRequest.getPickUpPassengerLongitude();
        String pickUpPassengerLatitude = orderRequest.getPickUpPassengerLatitude();

        QueryWrapper<OrderInfo> orderInfoQueryWrapper = new QueryWrapper<>();
        orderInfoQueryWrapper.eq("id", orderId);
        OrderInfo orderInfo = orderInfoMapper.selectOne(orderInfoQueryWrapper);

        //设置订单的状态：司机接到乘客，乘客上车
        orderInfo.setPickUpPassengerTime(LocalDateTime.now());
        orderInfo.setOrderStatus(OrderConstant.DRIVER_PICK_UP_PASSENGER);
        orderInfo.setPickUpPassengerLongitude(pickUpPassengerLongitude);
        orderInfo.setPickUpPassengerLatitude(pickUpPassengerLatitude);

        orderInfoMapper.updateById(orderInfo);

        return ResponseResult.success("");
    }

    /**
     * 司机行程结束，乘客下车
     *
     * @param orderRequest
     * @return
     */
    public ResponseResult passengerGetoff(OrderRequest orderRequest) {
        Long orderId = orderRequest.getOrderId();

        //获取乘客下车时的经度和纬度
        String passengerGetoffLongitude = orderRequest.getPassengerGetoffLongitude();
        String passengerGetoffLatitude = orderRequest.getPassengerGetoffLatitude();

        QueryWrapper<OrderInfo> orderInfoQueryWrapper = new QueryWrapper<>();
        orderInfoQueryWrapper.eq("id", orderId);
        OrderInfo orderInfo = orderInfoMapper.selectOne(orderInfoQueryWrapper);

        orderInfo.setOrderStatus(OrderConstant.PASSENGER_GET_OFF);
        //设置订单的状态：乘客下车
        orderInfo.setPassengerGetoffTime(LocalDateTime.now());
        orderInfo.setPassengerGetoffLongitude(passengerGetoffLongitude);
        orderInfo.setPassengerGetoffLatitude(passengerGetoffLatitude);

        //获取终端id
        ResponseResult<Car> car = serviceDriverUserClient.getCar(orderInfo.getCarId());
        String tid = car.getData().getTid();
        //载客起始时间戳 = 司机接到乘客，乘客上车的时间
        Long startTime = orderInfo.getPickUpPassengerTime().toInstant(ZoneOffset.of("+8")).toEpochMilli();
        //载客截止时间戳 = 乘客下车的时间，也就是现在
        Long endTime = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
        //司机行程结束时，订单中行驶的总路程和总时长
        ResponseResult<TrsearchTerminalResponse> trsearchTerminalResponseResponseResult = serviceMapClient.trsearchTerminal(tid + "", startTime, endTime);
        TrsearchTerminalResponse trsearchTerminalResponse = trsearchTerminalResponseResponseResult.getData();

        //距离
        Long driverMile = trsearchTerminalResponse.getDriverMile();
        //时长
        Long driverTime = trsearchTerminalResponse.getDriverTime();
        //获取订单中载客里程
        orderInfo.setDriveMile(driverMile);
        //获取订单中载客时长
        orderInfo.setDriveTime(driverTime);

        //距离
        int mile = driverMile.intValue();
        //时长
        int time = driverTime.intValue();
        //城市编码
        String cityCode = orderInfo.getAddress();
        //车辆类型
        String vehicleType = orderInfo.getVehicleType();
        CalculatePriceRequest calculatePriceRequest = new CalculatePriceRequest();
        calculatePriceRequest.setDistance(mile);
        calculatePriceRequest.setDuration(time);
        calculatePriceRequest.setCityCode(cityCode);
        calculatePriceRequest.setVehicleType(vehicleType);
        ResponseResult<Double> doubleResponseResult = servicePriceClient.calculatePrice(calculatePriceRequest);
        double price = doubleResponseResult.getData();

        orderInfo.setPrice(price);
        orderInfoMapper.updateById(orderInfo);
        return ResponseResult.success("");
    }

    /**
     * 司机发起收款
     *
     * @param orderRequest
     * @return
     */
    public ResponseResult pushPayInfo(OrderRequest orderRequest) {
        Long orderId = orderRequest.getOrderId();

        QueryWrapper<OrderInfo> orderInfoQueryWrapper = new QueryWrapper<>();
        orderInfoQueryWrapper.eq("id", orderId);
        OrderInfo orderInfo = orderInfoMapper.selectOne(orderInfoQueryWrapper);

        orderInfo.setOrderStatus(OrderConstant.INITIATE_COLLECTIONS);
        orderInfoMapper.updateById(orderInfo);
        return ResponseResult.success("");
    }

    /**
     * 乘客下车，订单支付完成
     *
     * @param orderRequest
     * @return
     */
    public ResponseResult pay(OrderRequest orderRequest) {
        Long orderId = orderRequest.getOrderId();

        QueryWrapper<OrderInfo> orderInfoQueryWrapper = new QueryWrapper<>();
        orderInfoQueryWrapper.eq("id", orderId);
        OrderInfo orderInfo = orderInfoMapper.selectOne(orderInfoQueryWrapper);

        orderInfo.setOrderStatus(OrderConstant.SUCCESS_PAY);

        orderInfoMapper.updateById(orderInfo);
        return ResponseResult.success("");
    }

    /**
     * 取消订单
     *
     * @param orderId
     * @param identity
     * @return
     */
    public ResponseResult cancel(Long orderId, String identity) {
        OrderInfo orderInfo = orderInfoMapper.selectById(orderId);
        Integer orderStatus = orderInfo.getOrderStatus();

        LocalDateTime cancelTime = LocalDateTime.now();
        Integer cancelOperator = null;
        Integer cancelTypeCode = null;

        int cancelOrder = 1;

        //司机接单时间
        LocalDateTime receiveOrderTime = null;
        //从司机接时间单到取消订单时间的中间时间差
        long between = 0L;
        //如果是乘客取消
        if (identity.trim().equals(IdentityConstant.PASSENGER_IDENTITY)) {
            cancelOperator = Integer.parseInt(IdentityConstant.PASSENGER_IDENTITY);
            switch (orderStatus) {
                //1.订单开始
                case OrderConstant.ORDER_START:
                    //乘客提前取消
                    cancelTypeCode = OrderConstant.CANCEL_PASSENGER_BEFORE;
                    break;
                //2.司机接单
                case OrderConstant.DRIVER_RECEIVE_ORDER:
                    //司机接单的时间
                    receiveOrderTime = orderInfo.getReceiveOrderTime();
                    between = ChronoUnit.MINUTES.between(receiveOrderTime, cancelTime);
                    if (between > 1) {
                        //乘客违约取消
                        cancelTypeCode = OrderConstant.CANCEL_PASSENGER_DEFAULT;
                    } else {
                        //乘客提前取消
                        cancelTypeCode = OrderConstant.CANCEL_PASSENGER_BEFORE;
                    }
                    break;
                //3.司机去接乘客
                case OrderConstant.DRIVER_TO_PICK_UP_PASSENGER:
                    //4.到达乘客出发点
                case OrderConstant.DRIVER_ARRIVED_DEPARTURE:
                    //乘客违约取消
                    cancelTypeCode = OrderConstant.CANCEL_PASSENGER_DEFAULT;
                    break;
                default:
                    log.info("取消订单失败");
                    cancelOrder = 0;
                    break;
            }
        }
        //如果是司机取消
        if (identity.trim().equals(IdentityConstant.DRIVER_IDENTITY)) {
            cancelOperator = Integer.parseInt(IdentityConstant.DRIVER_IDENTITY);
            switch (orderStatus) {
                //2.司机接单
                case OrderConstant.DRIVER_RECEIVE_ORDER:
                    //司机接单的时间
                    receiveOrderTime = orderInfo.getReceiveOrderTime();
                    between = ChronoUnit.MINUTES.between(receiveOrderTime, cancelTime);
                    if (between > 1) {
                        //司机违约取消
                        cancelTypeCode = OrderConstant.CANCEL_DRIVER_DEFAULT;
                    } else {
                        //司机提前取消
                        cancelTypeCode = OrderConstant.CANCEL_DRIVER_BEFORE;
                    }
                    break;
                //3.司机去接乘客
                case OrderConstant.DRIVER_TO_PICK_UP_PASSENGER:
                    //司机去接乘客的时间
                    LocalDateTime toPickUpPassengerTime = orderInfo.getToPickUpPassengerTime();
                    between = ChronoUnit.MINUTES.between(toPickUpPassengerTime, cancelTime);
                    if (between > 1) {
                        //司机违约取消
                        cancelTypeCode = OrderConstant.CANCEL_DRIVER_DEFAULT;
                    } else {
                        //司机提前取消
                        cancelTypeCode = OrderConstant.CANCEL_DRIVER_BEFORE;
                    }
                    break;
                //4.到达乘客出发点
                case OrderConstant.DRIVER_ARRIVED_DEPARTURE:
                    //司机到达乘客出发点的时间
                    LocalDateTime driverArrivedDepartureTime = orderInfo.getDriverArrivedDepartureTime();
                    between = ChronoUnit.MINUTES.between(driverArrivedDepartureTime, cancelTime);
                    if (between > 1) {
                        //司机违约取消
                        cancelTypeCode = OrderConstant.CANCEL_DRIVER_DEFAULT;
                    } else {
                        //司机提前取消
                        cancelTypeCode = OrderConstant.CANCEL_DRIVER_BEFORE;
                    }
                    break;
                default:
                    log.info("取消订单失败");
                    cancelOrder = 0;
                    break;
            }
        }

        //如果取消订单失败
        if (cancelOrder == 0) {
            return ResponseResult.fail(CommonStatusEnum.CANCEL_ORDER_FAIL.getCode(),
                    CommonStatusEnum.CANCEL_ORDER_FAIL.getMessage());
        } else {
            orderInfo.setCancelTime(cancelTime);
            orderInfo.setCancelOperator(cancelOperator);
            orderInfo.setCancelTypeCode(cancelTypeCode);
            orderInfo.setOrderStatus(OrderConstant.ORDER_CANCEL);

            orderInfoMapper.updateById(orderInfo);
            return ResponseResult.success("");
        }
    }

    public ResponseResult<OrderInfo> detail(Long orderId) {
        OrderInfo orderInfo = orderInfoMapper.selectById(orderId);
        return ResponseResult.success(orderInfo);
    }


    public ResponseResult<OrderInfo> current(String phone, String identity) {
        QueryWrapper<OrderInfo> queryWrapper = new QueryWrapper<>();

        if (identity.equals(IdentityConstant.DRIVER_IDENTITY)) {
            queryWrapper.eq("driver_phone", phone);

            queryWrapper.and(wrapper -> wrapper
                    .eq("order_status", OrderConstant.DRIVER_RECEIVE_ORDER)
                    .or().eq("order_status", OrderConstant.DRIVER_TO_PICK_UP_PASSENGER)
                    .or().eq("order_status", OrderConstant.DRIVER_ARRIVED_DEPARTURE)
                    .or().eq("order_status", OrderConstant.DRIVER_PICK_UP_PASSENGER)

            );
        }
        if (identity.equals(IdentityConstant.PASSENGER_IDENTITY)) {
            queryWrapper.eq("passenger_phone", phone);
            queryWrapper.and(wrapper -> wrapper.eq("order_status", OrderConstant.ORDER_START)
                    .or().eq("order_status", OrderConstant.DRIVER_RECEIVE_ORDER)
                    .or().eq("order_status", OrderConstant.DRIVER_TO_PICK_UP_PASSENGER)
                    .or().eq("order_status", OrderConstant.DRIVER_ARRIVED_DEPARTURE)
                    .or().eq("order_status", OrderConstant.DRIVER_PICK_UP_PASSENGER)
                    .or().eq("order_status", OrderConstant.PASSENGER_GET_OFF)
                    .or().eq("order_status", OrderConstant.INITIATE_COLLECTIONS)
            );
        }

        OrderInfo orderInfo = orderInfoMapper.selectOne(queryWrapper);
        return ResponseResult.success(orderInfo);
    }
}
