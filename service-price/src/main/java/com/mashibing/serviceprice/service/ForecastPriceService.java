package com.mashibing.serviceprice.service;

import com.mashibing.internalcommon.constant.CommonStatusEnum;
import com.mashibing.internalcommon.dto.PriceRule;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.ForecastPriceDTO;
import com.mashibing.internalcommon.response.DirectionResponse;
import com.mashibing.internalcommon.response.ForecastPriceResponse;
import com.mashibing.serviceprice.mapper.PriceRuleMapper;
import com.mashibing.serviceprice.remote.ServiceMapClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xcy
 * @date 2023/2/14 - 9:08
 */
@Service
@Slf4j
public class ForecastPriceService {

	@Autowired
	private ServiceMapClient serviceMapClient;

	@Autowired
	private PriceRuleMapper priceRuleMapper;

	public ResponseResult forecastPrice(String depLongitude, String depLatitude, String destLongitude, String destLatitude) {

		log.info("出发地经度: " + depLongitude);
		log.info("出发低纬度: " + depLatitude);
		log.info("目的地经度: " + destLongitude);
		log.info("目的地纬度: " + destLatitude);

		log.info("调用地图服务，查询距离和时长");
		ForecastPriceDTO forecastPriceDTO = new ForecastPriceDTO();
		forecastPriceDTO.setDepLongitude(depLongitude);
		forecastPriceDTO.setDepLatitude(depLatitude);
		forecastPriceDTO.setDestLongitude(destLongitude);
		forecastPriceDTO.setDestLatitude(destLatitude);
		ResponseResult<DirectionResponse> directionResponse = serviceMapClient.directionDriving(forecastPriceDTO);
		Integer distance = directionResponse.getData().getDistance();
		Integer duration = directionResponse.getData().getDuration();
		DirectionResponse direction = new DirectionResponse();
		direction.setDistance(distance);
		direction.setDuration(duration);
		log.info("距离：" + distance + "，时长：" + duration);
		//调用第三方接口API
		log.info("读取计价规则");
		Map<String, Object> queryMap = new HashMap<>();
		queryMap.put("city_code", "110000");
		queryMap.put("vehicle_type", "1");
		List<PriceRule> priceRules = priceRuleMapper.selectByMap(queryMap);

		//每一个城市的计价规则应该都只有一个
		if (priceRules.size() == 0) {
			return ResponseResult.fail(CommonStatusEnum.PRICE_RULE_NOT_EXISTS.getCode(), CommonStatusEnum.PRICE_RULE_NOT_EXISTS.getMessage());
		}

		PriceRule priceRule = priceRules.get(0);

		log.info("根据距离、时长、计价规则，计算价格");
		return ResponseResult.success(direction);
	}
}
