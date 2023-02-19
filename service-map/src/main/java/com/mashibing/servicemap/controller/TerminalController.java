package com.mashibing.servicemap.controller;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.TerminalResponse;
import com.mashibing.servicemap.service.TerminalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author xcy
 * @date 2023/2/19 - 7:19
 */
@RestController
public class TerminalController {

	@Autowired
	private TerminalService terminalService;

	@PostMapping("/terminal/add")
	public ResponseResult<TerminalResponse> addTerminal(@RequestParam String name) {
		return terminalService.addTerminal(name);
	}

	/*@GetMapping("/terminal/list")
	public ResponseResult selectTerminal(@RequestParam String tid) {
		return terminalService.selectTerminal(tid);
	}

	@PostMapping("/terminal/delete")
	public ResponseResult deleteTerminal(@RequestParam String tid) {
		return terminalService.deleteTerminal(tid);
	}*/
}
