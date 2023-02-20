package com.mashibing.serviceprice.controller;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.ForecastPriceDTO;
import com.mashibing.internalcommon.response.ForecastPriceResponse;
import com.mashibing.serviceprice.service.ForecastPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xcy
 * @date 2023/2/14 - 9:07
 */
@RestController
public class ForecastPriceController {

	@Autowired
	private ForecastPriceService forecastPriceService;

	@PostMapping("/forecast-price")
	public ResponseResult<ForecastPriceResponse> forecastPrice(@RequestBody ForecastPriceDTO forecastPriceDTO) {
		String depLongitude = forecastPriceDTO.getDepLongitude();
		String depLatitude = forecastPriceDTO.getDepLatitude();
		String destLongitude = forecastPriceDTO.getDestLongitude();
		String destLatitude = forecastPriceDTO.getDestLatitude();
		String cityCode = forecastPriceDTO.getCityCode();
		String vehicleType = forecastPriceDTO.getVehicleType();
		return forecastPriceService.forecastPrice(depLongitude, depLatitude, destLongitude, destLatitude,cityCode,vehicleType);
	}
}
