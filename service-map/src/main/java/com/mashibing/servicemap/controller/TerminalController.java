package com.mashibing.servicemap.controller;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.TerminalResponse;
import com.mashibing.internalcommon.response.TrsearchTerminalResponse;
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

	/**
	 * 创建终端
	 *
	 * @param name 终端名称
	 * @param desc 自定义参数
	 * @return
	 */
	@PostMapping("/terminal/add")
	public ResponseResult<TerminalResponse> addTerminal(@RequestParam String name, @RequestParam String desc) {
		return terminalService.addTerminal(name, desc);
	}

	/*@GetMapping("/terminal/list")
	public ResponseResult selectTerminal(@RequestParam String tid) {
		return terminalService.selectTerminal(tid);
	}

	@PostMapping("/terminal/delete")
	public ResponseResult deleteTerminal(@RequestParam String tid) {
		return terminalService.deleteTerminal(tid);
	}*/

	/**
	 * 查询轨迹信息
	 *
	 * @param tid       终端id
	 * @param startTime 载客起始时间戳
	 * @param endTime   载客截止时间戳
	 * @return
	 */
	@PostMapping("/terminal/trsearch")
	public ResponseResult<TrsearchTerminalResponse> trsearchTerminal(@RequestParam String tid, @RequestParam Long startTime, @RequestParam Long endTime) {
		return terminalService.trsearchTerminal(tid, startTime, endTime);
	}
}
