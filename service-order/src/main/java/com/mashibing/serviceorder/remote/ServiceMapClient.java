package com.mashibing.serviceorder.remote;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.TerminalResponse;
import com.mashibing.internalcommon.response.TrsearchTerminalResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author xcy
 * @date 2023/2/21 - 17:15
 */
@FeignClient("service-map")
public interface ServiceMapClient {

	@RequestMapping(method = RequestMethod.POST, value = "/around-search")
	public ResponseResult<List<TerminalResponse>> terminalAroundSearch(@RequestParam String center, @RequestParam Integer radius);

	@RequestMapping(method = RequestMethod.POST, value = "/terminal/trsearch")
	public ResponseResult<TrsearchTerminalResponse> trsearchTerminal(@RequestParam String tid, @RequestParam Long startTime, @RequestParam Long endTime);
}
