package com.mashibing.apiboss.remote;

import com.mashibing.internalcommon.dto.Car;
import com.mashibing.internalcommon.dto.DriverCarBindingRelationship;
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

	@RequestMapping(method = RequestMethod.POST, value = "/car")
	public ResponseResult addCar(@RequestBody Car car);

	@RequestMapping(method = RequestMethod.POST, value = "/driver-car-binding-relationship/bind")
	public ResponseResult bind(@RequestBody DriverCarBindingRelationship driverCarBindingRelationship);

	@RequestMapping(method = RequestMethod.POST, value = "/driver-car-binding-relationship/unbind")
	public ResponseResult unbind(@RequestBody DriverCarBindingRelationship driverCarBindingRelationship);
}
