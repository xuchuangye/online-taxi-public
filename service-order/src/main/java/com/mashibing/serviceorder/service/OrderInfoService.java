package com.mashibing.serviceorder.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mashibing.internalcommon.constant.CommonStatusEnum;
import com.mashibing.internalcommon.constant.OrderConstant;
import com.mashibing.internalcommon.dto.OrderInfo;
import com.mashibing.internalcommon.dto.PriceRule;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.OrderRequest;
import com.mashibing.serviceorder.mapper.OrderInfoMapper;
import com.mashibing.serviceorder.remote.ServicePriceClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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


		//校验乘客是否能够下单
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
