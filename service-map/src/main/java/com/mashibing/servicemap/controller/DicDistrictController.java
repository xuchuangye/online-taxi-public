package com.mashibing.servicemap.controller;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.servicemap.service.DicDistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xcy
 * @date 2023/2/15 - 10:59
 */
@RestController
public class DicDistrictController {

	@Autowired
	private DicDistrictService dicDistrictService;


	@GetMapping("/dic-district/{keywords}")
	public ResponseResult getDicDistrict(@PathVariable("keywords") String keywords) {

		return dicDistrictService.initDicDistrict(keywords);
	}
}
