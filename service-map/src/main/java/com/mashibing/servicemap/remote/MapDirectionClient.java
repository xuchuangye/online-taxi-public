package com.mashibing.servicemap.remote;

import com.mashibing.internalcommon.constant.DirectionConstant;
import com.mashibing.internalcommon.response.DirectionResponse;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author xcy
 * @date 2023/2/14 - 11:20
 */
@Service
@Slf4j
public class MapDirectionClient {

	/**
	 * key: 57503e34267a445386d2908e14d60d5a
	 */
	@Value("${amap.key}")
	private String aMapKey;

	@Autowired
	private RestTemplate restTemplate;

	/**
	 * @param depLongitude  起点经度
	 * @param depLatitude   起点维度
	 * @param destLongitude 终点经度
	 * @param destLatitude  终点纬度
	 * @return 返回距离和时长
	 */

	public DirectionResponse mapDirection(String depLongitude, String depLatitude, String destLongitude, String destLatitude) {
		//组装请求的URL
		StringBuilder urlBuilder = new StringBuilder();
		urlBuilder.append(DirectionConstant.DIRECTION_URL);
		urlBuilder.append("?");
		urlBuilder.append("origin=").append(depLongitude).append(",").append(depLatitude);
		urlBuilder.append("&");
		urlBuilder.append("destination=").append(destLongitude).append(",").append(destLatitude);
		urlBuilder.append("&");
		urlBuilder.append("extensions=all");
		urlBuilder.append("&");
		//要求必须是json格式
		//urlBuilder.append("output=xml");
		urlBuilder.append("output=json");
		urlBuilder.append("&");
		urlBuilder.append("key=").append(aMapKey);
		log.info("请求的URL：" + urlBuilder.toString());
		//调用高德地图的URL接口
		ResponseEntity<String> directionEntity = restTemplate.getForEntity(urlBuilder.toString(), String.class);

		String directionString = directionEntity.getBody();
		log.info("高德地图的路径规划，返回信息：" + directionString);
		//获得距离和时长
		return parseDirectionEntity(directionString);
	}

	/**
	 * 解析组装的url请求的JSON对象
	 *
	 * @param directionString JSON对象
	 * @return 解析出距离distance和时长duration
	 */
	public DirectionResponse parseDirectionEntity(String directionString) {
		DirectionResponse directionResponse = null;
		try {
			JSONObject result = JSONObject.fromObject(directionString);
			if (result.has(DirectionConstant.STATUS)) {
				int status = result.getInt(DirectionConstant.STATUS);
				if (status == 1) {
					if (result.has(DirectionConstant.ROUTE)) {
						JSONObject routeObject = result.getJSONObject(DirectionConstant.ROUTE);
						JSONArray pathsArray = routeObject.getJSONArray(DirectionConstant.PATHS);
						JSONObject pathObject = pathsArray.getJSONObject(0);

						directionResponse = new DirectionResponse();
						if (pathObject.has(DirectionConstant.DISTANCE)) {
							int distance = pathObject.getInt(DirectionConstant.DISTANCE);
							directionResponse.setDistance(distance);
						}

						if (pathObject.has(DirectionConstant.DURATION)) {
							int duration = pathObject.getInt(DirectionConstant.DURATION);
							directionResponse.setDuration(duration);
						}
					}
				}
			}
		} catch (Exception e) {

		}
		return directionResponse;
	}
}
