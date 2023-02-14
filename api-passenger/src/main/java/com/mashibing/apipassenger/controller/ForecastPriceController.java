package com.mashibing.apipassenger.controller;

import com.mashibing.apipassenger.service.ForecastPriceService;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.ForecastPriceDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xcy
 * @date 2023/2/13 - 17:30
 */
@RestController
public class ForecastPriceController {
	@Autowired
	private ForecastPriceService forecastPriceService;

	@PostMapping("/forecast-price")
	public ResponseResult forecastPrice(@RequestBody ForecastPriceDTO forecastPriceDTO) {

		return forecastPriceService.forecastPrice(forecastPriceDTO);
	}
}