package com.mashibing.servicemap.service;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.TerminalResponse;
import com.mashibing.internalcommon.response.TrsearchTerminalResponse;
import com.mashibing.servicemap.remote.ServiceFromMapClient;
import com.mashibing.servicemap.remote.TerminalClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xcy
 * @date 2023/2/19 - 7:23
 */
@Service
public class TerminalService {

	@Autowired
	private TerminalClient terminalClient;

	/**
	 * 创建终端
	 *
	 * @param name 终端名称
	 * @param desc 自定义参数
	 * @return
	 */
	public ResponseResult<TerminalResponse> addTerminal(String name, String desc) {
		return terminalClient.addTerminal(name, desc);
	}

	/**
	 * 查询轨迹信息
	 *
	 * @param tid       终端id
	 * @param startTime 载客起始时间戳
	 * @param endTime   载客截止时间戳
	 * @return
	 */
	public ResponseResult<TrsearchTerminalResponse> trsearchTerminal(String tid, Long startTime, Long endTime) {
		return terminalClient.trsearchTerminal(tid, startTime, endTime);
	}

	/*public ResponseResult deleteTerminal(String tid) {
		return terminalClient.deleteTerminal(tid);
	}

	public ResponseResult selectTerminal(String... tid) {
		return terminalClient.selectTerminal(tid);
	}*/
}
