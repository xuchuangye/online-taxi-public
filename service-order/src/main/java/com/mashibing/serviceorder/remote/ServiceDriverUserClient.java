package com.mashibing.serviceorder.remote;

import com.mashibing.internalcommon.dto.Car;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.OrderAboutDriverResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @author xcy
 * @date 2023/2/21 - 16:23
 */
@FeignClient("service-driver-user")
public interface ServiceDriverUserClient {

	@RequestMapping(method = RequestMethod.GET, value = "/city-driver/is-available-driver")
	public ResponseResult<Boolean> isAvailableDriver(@RequestParam String cityCode);

	@RequestMapping(method = RequestMethod.GET, value = "/get-available-driver/{carId}")
	public ResponseResult<OrderAboutDriverResponse> getAvailableDriver(@PathVariable("carId") Long carId);

	@RequestMapping(method = RequestMethod.GET, value = "/car")
	public ResponseResult<Car> getCar(@RequestParam Long carId);
}
