package com.mashibing.apidriver.remote;

import com.mashibing.apidriver.request.Car;
import com.mashibing.apidriver.request.DriverCarBindingRelationship;
import com.mashibing.apidriver.request.DriverUser;
import com.mashibing.apidriver.request.DriverUserWorkStatus;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.DriverUserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

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

	@RequestMapping(method = RequestMethod.GET, value = "/car")
	public ResponseResult<Car> getCar(@RequestParam Long carId);

	@RequestMapping(method = RequestMethod.PUT, value = "/driver-user-work-status")
	public ResponseResult changeWorkStatus(@RequestBody DriverUserWorkStatus driverUserWorkStatus);

	@RequestMapping(method = RequestMethod.GET, value = "/driver-car-binding-relationship")
	public ResponseResult<DriverCarBindingRelationship> getDriverCarBindingRelationship(@RequestParam String driverPhone);
}
