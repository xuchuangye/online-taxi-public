package com.mashibing.servicemap.service;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.TerminalResponse;
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

	public ResponseResult<TerminalResponse> addTerminal(String name) {
		return terminalClient.addTerminal(name);
	}
}