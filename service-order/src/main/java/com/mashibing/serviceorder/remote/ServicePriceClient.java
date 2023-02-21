package com.mashibing.serviceorder.remote;

import com.mashibing.internalcommon.dto.PriceRule;
import com.mashibing.internalcommon.dto.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
}
