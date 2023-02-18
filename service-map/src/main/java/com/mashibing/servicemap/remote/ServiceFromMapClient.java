package com.mashibing.servicemap.remote;

import com.mashibing.internalcommon.constant.MapConfigConstant;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.ServiceFromMapResponse;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author xcy
 * @date 2023/2/18 - 19:57
 */
@Service
public class ServiceFromMapClient {

	@Value("${amap.key}")
	private String aMapKey;

	@Autowired
	private RestTemplate restTemplate;

	public ResponseResult add(String name) {
		StringBuilder url = new StringBuilder();
		url.append(MapConfigConstant.SERVICE_ADD_URL);
		url.append("?");
		url.append("key=").append(aMapKey);
		url.append("&");
		url.append("name=").append(name);

		ResponseEntity<String> responseEntity = restTemplate.postForEntity(url.toString(), null, String.class);
		JSONObject body = JSONObject.fromObject(responseEntity.getBody());
		JSONObject data = body.getJSONObject("data");
		String sid = data.getString("sid");

		ServiceFromMapResponse serviceFromMapResponse = new ServiceFromMapResponse();
		serviceFromMapResponse.setSid(sid);

		return ResponseResult.success(serviceFromMapResponse);
	}
}
