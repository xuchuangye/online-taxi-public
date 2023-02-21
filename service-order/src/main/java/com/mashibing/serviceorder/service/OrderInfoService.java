package com.mashibing.serviceorder.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mashibing.internalcommon.constant.CommonStatusEnum;
import com.mashibing.internalcommon.constant.OrderConstant;
import com.mashibing.internalcommon.dto.OrderInfo;
import com.mashibing.internalcommon.dto.PriceRule;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.OrderRequest;
import com.mashibing.internalcommon.utils.RedisKeyUtils;
import com.mashibing.serviceorder.mapper.OrderInfoMapper;
import com.mashibing.serviceorder.remote.ServicePriceClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author xcy
 * @date 2023/2/20 - 7:55
 */
@Service
public class OrderInfoService {

	@Autowired
	private OrderInfoMapper orderInfoMapper;

	@Autowired
	private ServicePriceClient servicePriceClient;

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	/**
	 * 乘客下单
	 *
	 * @param orderRequest
	 * @return
	 */
	public ResponseResult addOrder(OrderRequest orderRequest) {
		//获取最新版本的计价规则
		ResponseResult<PriceRule> priceRuleResponseResult = servicePriceClient.getNewestVersion(orderRequest.getFareType());
		Integer fareVersion = priceRuleResponseResult.getData().getFareVersion();
		//判断当前版本是否是最新的
		ResponseResult<Boolean> newestVersionResponseResult = servicePriceClient.isNewestVersion(orderRequest.getFareType(), orderRequest.getFareVersion());
		boolean isNewestVersion = newestVersionResponseResult.getData();
		if (!isNewestVersion) {
			return ResponseResult.fail(CommonStatusEnum.PRICE_RULE_NOT_NEWEST.getCode(),
					CommonStatusEnum.PRICE_RULE_NOT_NEWEST.getMessage(),
					"最新的版本是：" + fareVersion);
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


		//判断是否有正在进行中的订单
		long count = isOrderGoingon(orderRequest);
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
		return ResponseResult.success("");
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

	private long isOrderGoingon(OrderRequest orderRequest) {
		QueryWrapper<OrderInfo> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("passenger_id", orderRequest.getPassengerId());
		queryWrapper.and(wrapper -> {
			wrapper.eq("order_status", OrderConstant.ORDER_START)
					.or().eq("order_status", OrderConstant.DRIVER_RECEIVE_ORDER)
					.or().eq("order_status", OrderConstant.DRIVER_TO_PICK_UP_PASSENGER)
					.or().eq("order_status", OrderConstant.DRIVER_ARRIVED_DEPARTURE)
					.or().eq("order_status", OrderConstant.DRIVER_PICK_UP_PASSENGER)
					.or().eq("order_status", OrderConstant.PASSENGER_GET_OFF)
					.or().eq("order_status", OrderConstant.INITIATE_COLLECTIONS);
		});

		return orderInfoMapper.selectCount(queryWrapper);
	}
}
