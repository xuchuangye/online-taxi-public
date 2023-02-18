package com.mashibing.servicemap.controller;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.servicemap.service.ServiceFromMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xcy
 * @date 2023/2/18 - 19:55
 */
@RestController
public class ServiceFromMapController {

	@Autowired
	private ServiceFromMapService serviceFromMapService;

	@PostMapping("/service/add")
	public ResponseResult addService(@RequestParam String name) {
		return serviceFromMapService.addService(name);
	}
}
