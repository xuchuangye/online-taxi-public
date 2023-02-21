package com.mashibing.servicemap.service;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.TerminalResponse;
import com.mashibing.servicemap.remote.AroundSearchClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xcy
 * @date 2023/2/19 - 17:19
 */
@Service
public class AroundSearchService {

	@Autowired
	private AroundSearchClient aroundSearchClient;

	public ResponseResult<List<TerminalResponse>> aroundSearch(String center, Integer radius) {
		return aroundSearchClient.aroundSearch(center, radius);
	}
}
