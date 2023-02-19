package com.mashibing.servicemap.controller;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.PointUploadRequest;
import com.mashibing.servicemap.service.PointUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xcy
 * @date 2023/2/19 - 11:21
 */
@RestController
public class PointController {
	@Autowired
	private PointUploadService pointUploadService;

	@PostMapping("/point/upload")
	public ResponseResult pointUpload(@RequestBody PointUploadRequest pointUploadRequest) {
		return pointUploadService.pointUpload(pointUploadRequest);
	}
}
