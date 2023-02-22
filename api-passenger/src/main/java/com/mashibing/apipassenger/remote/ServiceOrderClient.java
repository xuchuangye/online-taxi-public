package com.mashibing.apipassenger.remote;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.OrderRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @author xcy
 * @date 2023/2/20 - 7:56
 */
@FeignClient("service-order")
public interface ServiceOrderClient {

	@RequestMapping(method = RequestMethod.POST, value = "/order/add")
	public ResponseResult addOrder(@RequestBody OrderRequest orderRequest);

	@RequestMapping(method = RequestMethod.GET, value = "/test-real-time-order/{orderId}")
	public String dispatchRealTimeOrder(@PathVariable("orderId") Long orderId);
}
