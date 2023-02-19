package com.mashibing.servicemap.remote;

import com.mashibing.internalcommon.constant.MapConfigConstant;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.TerminalResponse;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author xcy
 * @date 2023/2/19 - 8:31
 */
@Service
public class TerminalClient {

	@Value("${amap.key}")
	private String aMapKey;

	@Value("${amap.sid}")
	private String sid;

	@Autowired
	private RestTemplate restTemplate;

	/**
	 * 创建终端
	 * https://tsapi.amap.com/v1/track/terminal/add?key=e0d3379f01867fcdb15286f434e7eaa3&sid=880995&name=%E5%88%9B%E5%BB%BA%E7%BB%88%E7%AB%AF
	 * @param name
	 * @return
	 */
	public ResponseResult<TerminalResponse> addTerminal(String name) {
		StringBuilder url = new StringBuilder();
		url.append(MapConfigConstant.TERMINAL_ADD_URL);
		url.append("?");
		url.append("key=").append(aMapKey);
		url.append("&");
		url.append("sid=").append(sid);
		url.append("&");
		url.append("name=").append(name);

		ResponseEntity<String> responseEntity = restTemplate.postForEntity(url.toString(), null, String.class);
		JSONObject body = JSONObject.fromObject(responseEntity.getBody());
		JSONObject data = body.getJSONObject("data");
		String tid = data.getString("tid");
		TerminalResponse terminalResponse = new TerminalResponse();
		terminalResponse.setTid(tid);

		return ResponseResult.success(terminalResponse);
	}
}
