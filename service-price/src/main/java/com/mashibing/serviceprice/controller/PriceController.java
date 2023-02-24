package com.mashibing.serviceprice.controller;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.CalculatePriceRequest;
import com.mashibing.internalcommon.request.ForecastPriceDTO;
import com.mashibing.internalcommon.response.ForecastPriceResponse;
import com.mashibing.serviceprice.service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author xcy
 * @date 2023/2/14 - 9:07
 */
@RestController
public class PriceController {

	@Autowired
	private PriceService priceService;

	/**
	 * 计算预估价格
	 *
	 * @param forecastPriceDTO
	 * @return
	 */
	@PostMapping("/forecast-price")
	public ResponseResult<ForecastPriceResponse> forecastPrice(@RequestBody ForecastPriceDTO forecastPriceDTO) {
		String depLongitude = forecastPriceDTO.getDepLongitude();
		String depLatitude = forecastPriceDTO.getDepLatitude();
		String destLongitude = forecastPriceDTO.getDestLongitude();
		String destLatitude = forecastPriceDTO.getDestLatitude();
		String cityCode = forecastPriceDTO.getCityCode();
		String vehicleType = forecastPriceDTO.getVehicleType();
		return priceService.forecastPrice(depLongitude, depLatitude, destLongitude, destLatitude, cityCode, vehicleType);
	}

	/**
	 * 计算实际价格
	 *
	 * @param calculatePriceRequest
	 * @return
	 */
	@PostMapping("/calculate-price")
	public ResponseResult calculatePrice(@RequestBody CalculatePriceRequest calculatePriceRequest) {
		Integer distance = calculatePriceRequest.getDistance();
		Integer duration = calculatePriceRequest.getDuration();
		String cityCode = calculatePriceRequest.getCityCode();
		String vehicleType = calculatePriceRequest.getVehicleType();
		return priceService.calculatePrice(distance, duration, cityCode, vehicleType);
	}
}
