package com.mashibing.apiboss.remote;

import com.mashibing.internalcommon.dto.DriverUser;
import com.mashibing.internalcommon.dto.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @author xcy
 * @date 2023/2/16 - 15:49
 */
@FeignClient("service-driver-user")
public interface ServiceDriverUserClient {

	@RequestMapping(method = RequestMethod.POST, value = "/user")
	public ResponseResult addDriverUser(@RequestBody DriverUser driverUser);

	@RequestMapping(method = RequestMethod.PUT, value = "/user")
	public ResponseResult updateDriverUser(@RequestBody DriverUser driverUser);
}
