package com.mashibing.apiboss.controller;

import com.mashibing.apiboss.service.DriverCarBindingRelationshipService;
import com.mashibing.internalcommon.dto.DriverCarBindingRelationship;
import com.mashibing.internalcommon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xcy
 * @date 2023/2/17 - 17:06
 */
@RestController
public class DriverCarBindingRelationshipController {

	@Autowired
	private DriverCarBindingRelationshipService driverCarBindingRelationshipService;

	@PostMapping("/driver-car-binding-relationship/bind")
	public ResponseResult bind(@RequestBody DriverCarBindingRelationship driverCarBindingRelationship) {
		return driverCarBindingRelationshipService.bind(driverCarBindingRelationship);
	}

	@PostMapping("/driver-car-binding-relationship/unbind")
	public ResponseResult unbind(@RequestBody DriverCarBindingRelationship driverCarBindingRelationship) {
		return driverCarBindingRelationshipService.unbind(driverCarBindingRelationship);
	}
}
