package com.mashibing.apiboss.remote;

import com.mashibing.internalcommon.dto.DriverUser;
import com.mashibing.internalcommon.dto.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author xcy
 * @date 2023/2/16 - 15:49
 */
@FeignClient("service-driver-user")
public interface ServiceDriverUserClient {

	@RequestMapping(method = RequestMethod.POST, value = "/user")
	public ResponseResult addDriverUser(@RequestBody DriverUser driverUser);
}
