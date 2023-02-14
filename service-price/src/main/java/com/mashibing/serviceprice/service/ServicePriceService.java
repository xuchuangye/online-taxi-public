package com.mashibing.serviceprice.service;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.ForecastPriceResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author xcy
 * @date 2023/2/14 - 9:08
 */
@Service
@Slf4j
public class ServicePriceService {

	public ResponseResult forecastPrice(
			String depLongitude,
			String depLatitude,
			String destLongitude,
			String destLatitude) {
		log.info("出发地经度: " + depLongitude);
		log.info("出发低纬度: " + depLatitude);
		log.info("目的地经度: " + destLongitude);
		log.info("目的地纬度: " + destLatitude);

		log.info("调用地图服务，查询距离和时长");
		log.info("读取计价规则");
		log.info("根据距离、时长、计价规则，计算价格");

		ForecastPriceResponse forecastPriceResponse = new ForecastPriceResponse();
		forecastPriceResponse.setPrice(12.36);
		return ResponseResult.success(forecastPriceResponse);
	}
}
