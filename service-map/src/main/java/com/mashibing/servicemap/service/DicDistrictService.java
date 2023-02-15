package com.mashibing.servicemap.service;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.servicemap.remote.MapDicDistrictClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xcy
 * @date 2023/2/15 - 11:00
 */
@Service
public class DicDistrictService {

	@Autowired
	private MapDicDistrictClient mapDicDistrictClient;

	public ResponseResult initDicDistrict(String keywords) {

		String dicDistrict = mapDicDistrictClient.dicDistrict(keywords);
		System.out.println(dicDistrict);
		//解析url

		//插入到数据库

		return ResponseResult.success("");
	}

}
