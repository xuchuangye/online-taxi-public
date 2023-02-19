package com.mashibing.servicemap.remote;

import com.mashibing.internalcommon.constant.MapConfigConstant;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.TerminalResponse;
import com.mashibing.internalcommon.response.TraceResponse;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author xcy
 * @date 2023/2/19 - 9:25
 */
@Service
public class TraceClient {

	@Value("${amap.key}")
	private String aMapKey;

	@Value("${amap.sid}")
	private String sid;

	@Autowired
	private RestTemplate restTemplate;

	/**
	 * 创建轨迹
	 *
	 * @return
	 */
	public ResponseResult<TraceResponse> addTrace(int tid) {
		StringBuilder url = new StringBuilder();
		url.append(MapConfigConstant.TRACE_ADD_URL);
		url.append("?");
		url.append("key=").append(aMapKey);
		url.append("&");
		url.append("sid=").append(sid);
		url.append("&");
		url.append("tid=").append(tid);

		ResponseEntity<String> responseEntity = restTemplate.postForEntity(url.toString(), null, String.class);
		JSONObject body = JSONObject.fromObject(responseEntity.getBody());
		JSONObject data = body.getJSONObject("data");
		int trid = data.getInt("trid");
		String trname = "";
		if (data.has("trname")) {
			trname = data.getString("trname");
		}
		TraceResponse traceResponse = new TraceResponse();
		//轨迹id
		traceResponse.setTrid(trid);
		//轨迹名称
		traceResponse.setTrname(trname);

		return ResponseResult.success(traceResponse);
	}
}
