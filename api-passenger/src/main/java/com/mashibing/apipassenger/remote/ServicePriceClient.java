package com.mashibing.apipassenger.remote;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.ForecastPriceDTO;
import com.mashibing.internalcommon.response.ForecastPriceResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author xcy
 * @date 2023/2/14 - 9:11
 */
@FeignClient("service-price")
public interface ServicePriceClient {

	@RequestMapping(method = RequestMethod.POST, value = "/forecast-price")
	public ResponseResult<ForecastPriceResponse> forecastPrice(@RequestBody ForecastPriceDTO forecastPriceDTO);
}
