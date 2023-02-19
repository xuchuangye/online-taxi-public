package com.mashibing.servicemap.service;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.TraceResponse;
import com.mashibing.servicemap.remote.TraceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xcy
 * @date 2023/2/19 - 9:24
 */
@Service
public class TraceService {

	@Autowired
	private TraceClient traceClient;

	public ResponseResult<TraceResponse> addTrace(String tid) {
		return traceClient.addTrace(tid);
	}
}
