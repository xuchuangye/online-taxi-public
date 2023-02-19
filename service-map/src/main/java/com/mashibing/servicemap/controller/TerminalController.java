package com.mashibing.servicemap.controller;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.TerminalResponse;
import com.mashibing.servicemap.service.TerminalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xcy
 * @date 2023/2/19 - 7:19
 */
@RestController
@RequestMapping("/terminal")
public class TerminalController {

	@Autowired
	private TerminalService terminalService;

	@PostMapping("/add")
	public ResponseResult<TerminalResponse> addTerminal(@RequestParam String name) {
		return terminalService.addTerminal(name);
	}
}
