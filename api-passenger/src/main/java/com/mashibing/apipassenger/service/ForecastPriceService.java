package com.mashibing.apipassenger.service;

import com.mashibing.apipassenger.remote.ServicePriceClient;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.ForecastPriceDTO;
import com.mashibing.internalcommon.response.ForecastPriceResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xcy
 * @date 2023/2/13 - 17:33
 */
@Service
@Slf4j
public class ForecastPriceService {

	@Autowired
	private ServicePriceClient servicePriceClient;

	public ResponseResult forecastPrice(ForecastPriceDTO forecastPriceDTO) {
		String depLongitude = forecastPriceDTO.getDepLongitude();
		String depLatitude = forecastPriceDTO.getDepLatitude();
		String destLongitude = forecastPriceDTO.getDestLongitude();
		String destLatitude = forecastPriceDTO.getDestLatitude();

		log.info("出发地经度: " + depLongitude);
		log.info("出发低纬度: " + depLatitude);
		log.info("目的地经度: " + destLongitude);
		log.info("目的地纬度: " + destLatitude);

		servicePriceClient.forecastPrice(forecastPriceDTO);

		log.info("调用地图服务，预估价格");

		ForecastPriceResponse forecastPriceResponse = new ForecastPriceResponse();
		forecastPriceResponse.setPrice(12.36);
		return ResponseResult.success(forecastPriceResponse);
	}
}
