package com.mashibing.servicemap.remote;

import com.mashibing.internalcommon.constant.MapConfigConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author xcy
 * @date 2023/2/15 - 15:10
 */
@Service
public class MapDicDistrictClient {

	@Value("${amap.key}")
	private String aMapKey;

	@Autowired
	private RestTemplate restTemplate;

	/**
	 *
	 * @param keywords 查询关键字：具体的国家、省、市
	 * @return 返回解析出的请求地图地区字典
	 */
	public String dicDistrict(String keywords) {
		//组装请求的url
		StringBuilder url = assemblyUrl(keywords);
		//执行url的请求
		ResponseEntity<String> dicDistrictEntity = restTemplate.getForEntity(url.toString(), String.class);
		//返回结果
		return dicDistrictEntity.getBody();
	}

	/**
	 * 拼装行政区域查询的地区字典的请求地址url
	 *
	 * @param keywords 查询关键字
	 * @return 返回请求地址url
	 */
	private StringBuilder assemblyUrl(String keywords) {
		StringBuilder url = new StringBuilder();
		url.append(MapConfigConstant.DISTRICT_URL);
		url.append("?");
		url.append("keywords=").append(keywords);
		url.append("&");
		url.append("subdistrict=3");
		url.append("&");
		url.append("key=").append(aMapKey);
		return url;
	}
}
