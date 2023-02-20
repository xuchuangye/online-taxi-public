package com.mashibing.serviceorder.service;

import com.mashibing.internalcommon.dto.OrderInfo;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.OrderRequest;
import com.mashibing.serviceorder.mapper.OrderInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xcy
 * @date 2023/2/20 - 7:55
 */
@Service
public class OrderInfoService {

	@Autowired
	private OrderInfoMapper orderInfoMapper;

	public ResponseResult addOrder(OrderRequest orderRequest) {
		OrderInfo orderInfo = new OrderInfo();
		orderInfoMapper.insert(orderInfo);
		return ResponseResult.success("");
	}
}
