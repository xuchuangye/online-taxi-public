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

import java.math.BigDecimal;
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
		double price = getPrice(distance, duration, priceRule);

		return ResponseResult.success(price);
	}

	/**
	 * 根据距离、时长和计价规则计算价格
	 *
	 * @param distance  距离
	 * @param duration  时长
	 * @param priceRule 计价规则
	 * @return 总费用
	 */
	public double getPrice(int distance, int duration, PriceRule priceRule) {
		//总费用
		BigDecimal price = new BigDecimal(0);

		//起步价
		BigDecimal startFare = BigDecimal.valueOf(priceRule.getStartFare());
		price = price.add(startFare);

		//里程费
		//起步里程：公里
		BigDecimal startMile = new BigDecimal(priceRule.getStartMile());
		//需要计费的里程 = 总里程（米转换成公里） - 起步里程（公里）
		BigDecimal subtract = new BigDecimal(distance).divide(new BigDecimal(1000), 2, BigDecimal.ROUND_HALF_UP).subtract(startMile);
		//只有需要计算的里程超过起步里程，才需要计价，否则不计价
		double sub = subtract.doubleValue() < 0 ? 0 : subtract.doubleValue();

		//计程单价
		BigDecimal unitPricePreMile = BigDecimal.valueOf(priceRule.getUnitPricePreMile());
		//里程费 = 计程单价 * 需要计费的里程
		BigDecimal mileFarePrice = unitPricePreMile.multiply(BigDecimal.valueOf(sub));
		price = price.add(mileFarePrice);

		//时长费
		//时长：秒转换成分钟，并且保留小数点后两位，四舍五入
		BigDecimal durationBigDecimal = new BigDecimal(duration).divide(new BigDecimal(60), 2, BigDecimal.ROUND_HALF_UP);
		//计时单价
		BigDecimal unitPricePreMinute = BigDecimal.valueOf(priceRule.getUnitPricePreMinute());
		//时长费 = 时长 * 计时单价
		BigDecimal durationPrice = durationBigDecimal.multiply(unitPricePreMinute);
		price = price.add(durationPrice);

		return price.doubleValue();
	}

	/*public static void main(String[] args) {
		PriceRule priceRule = new PriceRule("110000", "1",10.0,3,1.8,0.5);

		System.out.println(getPrice(6500, 1800, priceRule));
	}*/
}
