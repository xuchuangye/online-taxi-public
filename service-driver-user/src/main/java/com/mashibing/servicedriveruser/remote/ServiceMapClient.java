package com.mashibing.servicedriveruser.remote;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.TerminalResponse;
import com.mashibing.internalcommon.response.TraceResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author xcy
 * @date 2023/2/19 - 9:04
 */
@FeignClient("service-map")
public interface ServiceMapClient {

	@RequestMapping(method = RequestMethod.POST, value = "/terminal/add")
	public ResponseResult<TerminalResponse> addTerminal(@RequestParam String name, @RequestParam String desc);

	@RequestMapping(method = RequestMethod.POST, value = "/trace/add")
	public ResponseResult<TraceResponse> addTrace(@RequestParam Integer tid);
}
