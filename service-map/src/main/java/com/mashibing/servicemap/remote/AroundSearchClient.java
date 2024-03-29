package com.mashibing.servicemap.remote;

import com.mashibing.internalcommon.constant.MapConfigConstant;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.TerminalResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

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
	 * https://tsapi.amap.com/v1/track/terminal/aroundsearch?key=e0d3379f01867fcdb15286f434e7eaa3&sid=880995&center=39.98%2C116.38&radius=1000
	 *
	 * @param center 中心坐标
	 * @param radius 半径
	 * @return
	 */
	public ResponseResult<List<TerminalResponse>> aroundSearch(String center, Integer radius) {
		StringBuilder url = new StringBuilder();
		url.append(MapConfigConstant.TERMINAL_AROUND_SEARCH_URL);
		url.append("?");
		url.append("key=").append(aMapKey);
		url.append("&");
		url.append("sid=").append(sid);
		url.append("&");
		url.append("center=").append(center);
		url.append("&");
		url.append("radius=").append(radius);

		System.out.println("终端周边搜索的请求：" + url.toString());
		ResponseEntity<String> responseEntity = restTemplate.postForEntity(URI.create(url.toString()), null, String.class);
		System.out.println("终端周边搜索的响应：" + responseEntity.getBody());
		JSONObject body = JSONObject.fromObject(responseEntity.getBody());
		JSONObject data = body.getJSONObject("data");

		List<TerminalResponse> lists = new ArrayList<>();
		JSONArray resultsArray = data.getJSONArray(MapConfigConstant.RESULTS);

		for (int i = 0; i < resultsArray.size(); i++) {
			TerminalResponse terminalResponse = new TerminalResponse();

			JSONObject result = resultsArray.getJSONObject(i);

			//desc是carId
			Integer tid = result.getInt(MapConfigConstant.TID);
			Long carId = Long.parseLong(result.getString(MapConfigConstant.DESC));

			JSONObject location = result.getJSONObject("location");
			//获取经度
			String longitude = location.getString("longitude");
			//获取经度
			String latitude = location.getString("latitude");

			terminalResponse.setTid(tid);
			terminalResponse.setDesc(carId);
			terminalResponse.setLongitude(longitude);
			terminalResponse.setLatitude(latitude);
			lists.add(terminalResponse);
		}
		return ResponseResult.success(lists);
	}
}
