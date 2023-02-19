package com.mashibing.apidriver.controller;

import com.mashibing.apidriver.service.PointService;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.ApiDriverPointRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xcy
 * @date 2023/2/19 - 15:24
 */
@RestController
public class PointController {

	@Autowired
	private PointService pointService;

	@PostMapping("/point/upload")
	public ResponseResult upload(@RequestBody ApiDriverPointRequest apiDriverPointRequest) {
		return pointService.upload(apiDriverPointRequest);
	}
}
