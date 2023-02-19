package com.mashibing.servicemap.remote;

import com.mashibing.internalcommon.constant.MapConfigConstant;
import com.mashibing.internalcommon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

/**
 * @author xcy
 * @date 2023/2/19 - 17:20
 */
@Service
public class AroundSearchClient {

	@Value("${amap.key}")
	private String aMapKey;

	@Value("${amap.sid}")
	private String sid;

	@Autowired
	private RestTemplate restTemplate;

	/**
	 * 周边搜索
	 * https://tsapi.amap.com/v1/track/terminal/aroundsearch
	 * ?
	 * key=e0d3379f01867fcdb15286f434e7eaa3
	 * &
	 * sid=880995
	 * &
	 * center=39.98%2C116.38
	 * &
	 * radius=1000
	 *
	 * @param center 中心坐标
	 * @param radius 半径
	 * @return
	 */
	public ResponseResult aroundSearch(String center, String radius) {
		StringBuilder url = new StringBuilder();
		url.append(MapConfigConstant.AROUND_SEARCH_URL);
		url.append("?");
		url.append("key=").append(aMapKey);
		url.append("&");
		url.append("sid=").append(sid);
		url.append("&");
		url.append("center=").append(center);
		url.append("&");
		url.append("radius=").append(radius);

		System.out.println("周边搜索的请求：" + url.toString());
		ResponseEntity<String> responseEntity = restTemplate.postForEntity(URI.create(url.toString()), null, String.class);
		System.out.println("周边搜索的响应：" + responseEntity.getBody());
		return ResponseResult.success("");
	}
}
