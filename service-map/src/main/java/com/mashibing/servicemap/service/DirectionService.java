package com.mashibing.servicemap.service;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.DirectionResponse;
import com.mashibing.internalcommon.response.ForecastPriceResponse;
import com.mashibing.servicemap.remote.MapDirectionClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xcy
 * @date 2023/2/14 - 9:52
 */
@Service
@Slf4j
public class DirectionService {

	@Autowired
	private MapDirectionClient mapDirectionClient;

	/**
	 * 根据起点和终点的经纬度计算距离（单位：米）和时长（单位：分）
	 *
	 * @param depLongitude  起点经度
	 * @param depLatitude   起点维度
	 * @param destLongitude 终点经度
	 * @param destLatitude  终点维度
	 * @return
	 */
	public ResponseResult directionDriving(
			String depLongitude,
			String depLatitude,
			String destLongitude,
			String destLatitude) {
		log.info("出发地经度: " + depLongitude);
		log.info("出发低纬度: " + depLatitude);
		log.info("目的地经度: " + destLongitude);
		log.info("目的地纬度: " + destLatitude);

		log.info("调用地图服务，查询距离和时长");
		ForecastPriceResponse directionResponse = mapDirectionClient.direction(depLongitude, depLatitude, destLongitude, destLatitude);
		log.info("读取计价规则");
		log.info("根据距离、时长、计价规则，计算价格");
		return ResponseResult.success(directionResponse);
	}
}
