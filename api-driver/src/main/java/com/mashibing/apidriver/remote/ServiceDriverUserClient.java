package com.mashibing.apidriver.remote;

import com.mashibing.internalcommon.dto.DriverUser;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.DriverUserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author xcy
 * @date 2023/2/16 - 20:10
 */
@FeignClient("service-driver-user")
public interface ServiceDriverUserClient {

	@RequestMapping(method = RequestMethod.PUT, value = "/user")
	public ResponseResult updateDriverUser(@RequestBody DriverUser driverUser);

	@RequestMapping(method = RequestMethod.GET, value = "/user/{driver-phone}")
	public ResponseResult<DriverUserResponse> selectDriverUser(@PathVariable("driver-phone") String driverPhone);
}
