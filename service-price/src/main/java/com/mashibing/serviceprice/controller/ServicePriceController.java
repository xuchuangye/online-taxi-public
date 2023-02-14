package com.mashibing.serviceprice.controller;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.ForecastPriceDTO;
import com.mashibing.serviceprice.service.ServicePriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xcy
 * @date 2023/2/14 - 9:07
 */
@RestController
public class ServicePriceController {

	@Autowired
	private ServicePriceService servicePriceService;

	@PostMapping("/forecast-price")
	public ResponseResult forecastPrice(@RequestBody ForecastPriceDTO forecastPriceDTO) {
		String depLongitude = forecastPriceDTO.getDepLongitude();
		String depLatitude = forecastPriceDTO.getDepLatitude();
		String destLongitude = forecastPriceDTO.getDestLongitude();
		String destLatitude = forecastPriceDTO.getDestLatitude();

		return servicePriceService.forecastPrice(depLongitude, depLatitude, destLongitude, destLatitude);
	}
}
