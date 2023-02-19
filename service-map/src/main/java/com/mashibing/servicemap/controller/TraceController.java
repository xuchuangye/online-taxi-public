package com.mashibing.servicemap.controller;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.TraceResponse;
import com.mashibing.servicemap.service.TraceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xcy
 * @date 2023/2/19 - 9:23
 */
@RestController
@RequestMapping("/trace")
public class TraceController {
	@Autowired
	private TraceService traceService;

	@PostMapping("/add")
	public ResponseResult<TraceResponse> addTrace(@RequestParam int tid) {
		return traceService.addTrace(tid);
	}
}
