package com.mashibing.servicemap.remote;

import com.mashibing.internalcommon.constant.MapConfigConstant;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.TerminalResponse;
import com.mashibing.internalcommon.response.TrsearchTerminalResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

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
	 *
	 * @param name
	 * @return
	 */
	public ResponseResult<TerminalResponse> addTerminal(String name, String desc) {
		StringBuilder url = new StringBuilder();
		url.append(MapConfigConstant.TERMINAL_ADD_URL);
		url.append("?");
		url.append("key=").append(aMapKey);
		url.append("&");
		url.append("sid=").append(sid);
		url.append("&");
		url.append("name=").append(name);
		url.append("&");
		url.append("desc=").append(desc);

		System.out.println("创建终端的请求：" + url.toString());
		ResponseEntity<String> responseEntity = restTemplate.postForEntity(url.toString(), null, String.class);
		System.out.println("创建终端的响应：" + responseEntity.getBody());

		JSONObject body = JSONObject.fromObject(responseEntity.getBody());
		JSONObject data = body.getJSONObject("data");
		Integer tid = data.getInt("tid");
		TerminalResponse terminalResponse = new TerminalResponse();
		terminalResponse.setTid(tid);

		return ResponseResult.success(terminalResponse);
	}

	/**
	 * 查询轨迹信息
	 * https://tsapi.amap.com/v1/track/terminal/trsearch?key=e0d3379f01867fcdb15286f434e7eaa3&sid=880995&tid=639021815&trid=380&starttime=1677143361903&endtime=1677143429344
	 *
	 * @param tid       终端id
	 * @param startTime 载客起始时间戳
	 * @param endTime   载客截止时间戳
	 * @return
	 */
	public ResponseResult<TrsearchTerminalResponse> trsearchTerminal(String tid, Long startTime, Long endTime) {
		StringBuilder url = new StringBuilder();
		url.append(MapConfigConstant.TRSEARCH_TERMINAL);
		url.append("?");
		url.append("key=").append(aMapKey);
		url.append("&");
		url.append("sid=").append(sid);
		url.append("&");
		url.append("tid=").append(tid);
		url.append("&");
		url.append("starttime=").append(startTime);
		url.append("&");
		url.append("endtime=").append(endTime);

		System.out.println("查询轨迹信息的请求：" + url.toString());
		ResponseEntity<String> responseEntity = restTemplate.getForEntity(url.toString(), String.class);
		System.out.println("查询轨迹信息的响应：" + responseEntity.getBody());

		JSONObject body = JSONObject.fromObject(responseEntity.getBody());
		JSONObject data = body.getJSONObject("data");

		JSONArray tracks = data.getJSONArray("tracks");

		TrsearchTerminalResponse trsearchTerminalResponse = new TrsearchTerminalResponse();

		long driverMile = 0L;
		long driverTime = 0L;
		for (int i = 0; i < tracks.size(); i++) {
			JSONObject track = tracks.getJSONObject(i);
			//载客里程
			long distance = track.getLong("distance");
			driverMile += distance;
			//载客时间
			long time = track.getLong("time") / (1000 * 60);
			driverTime += time;
		}
		trsearchTerminalResponse.setDriverMile(driverMile);
		trsearchTerminalResponse.setDriverTime(driverTime);
		return ResponseResult.success(trsearchTerminalResponse);
	}

	/**
	 * 删除终端
	 * https://tsapi.amap.com/v1/track/terminal/delete?key=e0d3379f01867fcdb15286f434e7eaa3&sid=880995&tid=637612857
	 * @param tid
	 * @return
	 */
	/*public ResponseResult deleteTerminal(String tid) {
		StringBuilder url = new StringBuilder();
		url.append(MapConfigConstant.TERMINAL_DELETE_URL);
		url.append("?");
		url.append("key=").append(aMapKey);
		url.append("&");
		url.append("sid=").append(sid);
		url.append("&");
		url.append("tid=").append(tid);
		return ResponseResult.success("");
	}*/

	/**
	 * 查询终端
	 * https://tsapi.amap.com/v1/track/terminal/list?key=e0d3379f01867fcdb15286f434e7eaa3&sid=880995&tid=637214693
	 * @param tid 多个或两个参数
	 * @return
	 */
	/*public ResponseResult selectTerminal(String... tid) {
		StringBuilder url = new StringBuilder();
		url.append(MapConfigConstant.TERMINAL_DELETE_URL);
		url.append("?");
		url.append("key=").append(aMapKey);
		url.append("&");
		url.append("sid=").append(sid);
		url.append("&");
		url.append("tid=").append(tid);

		ResponseEntity<String> responseEntity = restTemplate.getForEntity(url.toString(), String.class);
		JSONObject jsonObject = JSONObject.fromObject(responseEntity.getBody());

		return ResponseResult.success("");
	}*/
}
