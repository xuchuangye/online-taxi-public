package com.mashibing.serviceorder.remote;

import com.mashibing.internalcommon.dto.PriceRule;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.CalculatePriceRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @author xcy
 * @date 2023/2/20 - 20:39
 */
@FeignClient("service-price")
public interface ServicePriceClient {

	@RequestMapping(method = RequestMethod.GET, value = "/price-rule/get-newest-version")
	public ResponseResult<PriceRule> getNewestVersion(@RequestParam String fareType);

	@RequestMapping(method = RequestMethod.GET, value = "/price-rule/is-newest-version")
	public ResponseResult<Boolean> isNewestVersion(@RequestParam String fareType, @RequestParam Integer fareVersion);

	@RequestMapping(method = RequestMethod.GET, value = "/price-rule/is-exists")
	public ResponseResult<Boolean> isExists(@RequestParam String cityCode, @RequestParam String vehicleType);


	/**
	 * 计算实际价格
	 *
	 * @param calculatePriceRequest
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/calculate-price")
	public ResponseResult<Double> calculatePrice(@RequestBody CalculatePriceRequest calculatePriceRequest);
}
