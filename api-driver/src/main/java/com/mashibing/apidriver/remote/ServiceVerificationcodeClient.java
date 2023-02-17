package com.mashibing.apidriver.remote;

import com.mashibing.internalcommon.dto.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author xcy
 * @date 2023/2/17 - 18:31
 */
@FeignClient("service-verificationcode")
public interface ServiceVerificationcodeClient {

	@RequestMapping(method = RequestMethod.GET, value = "/numberCode/{size}")
	public ResponseResult numberCode(@PathVariable("size") int size);
}
