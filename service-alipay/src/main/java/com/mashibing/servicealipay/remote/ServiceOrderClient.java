package com.mashibing.servicealipay.remote;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.OrderRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author xcy
 * @date 2023/2/25 - 8:08
 */
@FeignClient("service-order")
public interface ServiceOrderClient {

	@RequestMapping(method = RequestMethod.POST, value = "/order/pay")
	public ResponseResult pay(@RequestBody OrderRequest orderRequest);
}
