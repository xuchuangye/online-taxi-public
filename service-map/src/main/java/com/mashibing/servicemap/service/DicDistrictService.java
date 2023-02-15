package com.mashibing.servicemap.service;

import com.mashibing.internalcommon.constant.MapConfigConstant;
import com.mashibing.internalcommon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author xcy
 * @date 2023/2/15 - 11:00
 */
@Service
public class DicDistrictService {
	@Value("$amap.key")
	private String aMapKey;

	public ResponseResult getDicDistrict(String keywords) {
		//组装请求的url
		StringBuilder url = assemblyUrl(keywords);
		//解析url

		//
		return ResponseResult.success("");
	}

	/**
	 * 拼装行政区域查询的地区字典的请求地址url
	 *
	 * @param keywords
	 * @return
	 */
	private StringBuilder assemblyUrl(String keywords) {
		StringBuilder url = new StringBuilder();
		url.append(MapConfigConstant.DISTRICT_URL);
		url.append("?");
		url.append("keywords=").append(keywords);
		url.append("&");
		url.append("subdistrict=2");
		url.append("&");
		url.append("key=").append(aMapKey);
		return url;
	}


}
