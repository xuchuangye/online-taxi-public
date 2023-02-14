package com.mashibing.servicemap.remote;

import com.mashibing.internalcommon.constant.DirectionConstant;
import com.mashibing.internalcommon.response.ForecastPriceResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author xcy
 * @date 2023/2/14 - 11:20
 */
@Component
public class MapDirectionClient {

	/**
	 * key: 57503e34267a445386d2908e14d60d5a
	 */
	@Value("${amap.key}")
	private String aMapKey;

	/**
	 * @param depLongitude  起点经度
	 * @param depLatitude   起点维度
	 * @param destLongitude 终点经度
	 * @param destLatitude  终点纬度
	 * @return 返回距离和时长
	 */

	public ForecastPriceResponse direction(
			String depLongitude,
			String depLatitude,
			String destLongitude,
			String destLatitude) {

		//组装请求的URL
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(DirectionConstant.DIRECTION_URL);
		stringBuilder.append("?");
		stringBuilder.append("origin=").append(depLongitude).append(",").append(depLatitude);
		stringBuilder.append("&");
		stringBuilder.append("destination=").append(destLongitude).append(",").append(destLatitude);
		stringBuilder.append("&");
		stringBuilder.append("extensions=all");
		stringBuilder.append("&");
		stringBuilder.append("output=xml");
		stringBuilder.append("&");
		stringBuilder.append("key=").append(aMapKey);

		System.out.println(stringBuilder.toString());
		//调用URL

		//获得距离和时长

		return null;
	}
}
