package com.mashibing.servicemap.controller;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.TerminalResponse;
import com.mashibing.servicemap.service.AroundSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author xcy
 * @date 2023/2/19 - 17:19
 */
@RestController
public class AroundSearchController {

	@Autowired
	private AroundSearchService aroundSearchService;

	@PostMapping("/around-search")
	public ResponseResult<List<TerminalResponse>> aroundSearch(@RequestParam String center, @RequestParam Integer radius) {
		return aroundSearchService.aroundSearch(center, radius);
	}
}
