package com.mashibing.serviceorder.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mashibing.internalcommon.constant.CommonStatusEnum;
import com.mashibing.internalcommon.constant.OrderConstant;
import com.mashibing.internalcommon.dto.OrderInfo;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.OrderRequest;
import com.mashibing.internalcommon.response.TerminalResponse;
import com.mashibing.internalcommon.utils.RedisKeyUtils;
import com.mashibing.serviceorder.mapper.OrderInfoMapper;
import com.mashibing.serviceorder.remote.ServiceDriverUserClient;
import com.mashibing.serviceorder.remote.ServiceMapClient;
import com.mashibing.serviceorder.remote.ServicePriceClient;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

	/**
	 * 乘客下单
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

		//判断是否有正在进行中的订单
		int count = isOrderGoingon(orderRequest.getPassengerId());
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
		aroundSearchAvailableDriver(orderRequest);
		return ResponseResult.success("");
	}

	@Autowired
	private ServiceMapClient serviceMapClient;

	/**
	 * 终端周边搜索可用司机
	 *
	 * @param orderRequest
	 */
	public void aroundSearchAvailableDriver(OrderRequest orderRequest) {
		//出发地纬度
		String depLatitude = orderRequest.getDepLatitude();
		//出发地经度
		String depLongitude = orderRequest.getDepLongitude();
		String center = depLatitude + "," + depLongitude;


		/*ResponseResult<List<TerminalResponse>> listResponseResult = serviceMapClient.aroundSearch(center, radius);
		List<TerminalResponse> list = listResponseResult.getData();
		if (list.size() == 0) {
			radius = 4000;
			listResponseResult = serviceMapClient.aroundSearch(center, radius);
			list = listResponseResult.getData();
			if (list.size() == 0) {
				radius = 5000;
				listResponseResult = serviceMapClient.aroundSearch(center, radius);
				list = listResponseResult.getData();
				if (list.size() == 0) {
					log.info("此次派单没有找到司机终端，终端搜索2KM、4KM、5KM");
				}
			}
		}*/
		ResponseResult<List<TerminalResponse>> listResponseResult = null;
		List<TerminalResponse> list = null;
		for (int radius = 2000; radius < 6000; ) {
			listResponseResult = serviceMapClient.aroundSearch(center, radius);
			log.info("" + JSONArray.fromObject(listResponseResult.getData()));
			log.info("此次派单的终端周边搜索在" + radius + "KM范围内进行搜索");
			//查询终端

			//解析终端

			//根据解析终端获取车辆信息

			//找到符合的车辆，进行派单

			//如果派单成功，则退出循环

			list = listResponseResult.getData();
			if (list.size() == 0) {
				radius += 2000;
			}else {
				break;
			}
		}

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

	private int isOrderGoingon(Long passengerId) {
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
}
