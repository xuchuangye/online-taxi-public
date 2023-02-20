package com.mashibing.apipassenger.service;

import com.mashibing.apipassenger.remote.ServiceOrderClient;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.OrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xcy
 * @date 2023/2/20 - 7:30
 */
@Service
public class OrderService {

	@Autowired
	private ServiceOrderClient serviceOrderClient;

	public ResponseResult add(OrderRequest orderRequest) {
		return serviceOrderClient.addOrder(orderRequest);
	}
}
