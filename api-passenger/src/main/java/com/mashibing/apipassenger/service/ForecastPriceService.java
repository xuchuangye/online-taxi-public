package com.mashibing.apipassenger.service;

import com.mashibing.apipassenger.remote.ServicePriceClient;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.ForecastPriceDTO;
import com.mashibing.internalcommon.response.ForecastPriceResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
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

	public ResponseResult forecastPrice(String depLongitude,
	                                    String depLatitude,
	                                    String destLongitude,
	                                    String destLatitude,
	                                    String cityCode,
	                                    String vehicleType) {
		ForecastPriceDTO forecastPriceDTO = new ForecastPriceDTO();
		forecastPriceDTO.setDepLongitude(depLongitude);
		forecastPriceDTO.setDepLatitude(depLatitude);
		forecastPriceDTO.setDestLongitude(destLongitude);
		forecastPriceDTO.setDestLatitude(destLatitude);
		forecastPriceDTO.setCityCode(cityCode);
		forecastPriceDTO.setVehicleType(vehicleType);

		log.info("出发地经度: " + depLongitude);
		log.info("出发低纬度: " + depLatitude);
		log.info("目的地经度: " + destLongitude);
		log.info("目的地纬度: " + destLatitude);


		log.info("调用地图服务，预估价格");
		ResponseResult<ForecastPriceResponse> priceResponseResponseResult = servicePriceClient.forecastPrice(forecastPriceDTO);
		ForecastPriceResponse data = priceResponseResponseResult.getData();

		ForecastPriceResponse forecastPriceResponse = new ForecastPriceResponse();
		BeanUtils.copyProperties(data, forecastPriceResponse);
		return ResponseResult.success(forecastPriceResponse);
	}
}
