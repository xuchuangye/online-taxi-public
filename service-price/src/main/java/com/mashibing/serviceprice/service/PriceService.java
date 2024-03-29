package com.mashibing.serviceprice.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mashibing.internalcommon.constant.CommonStatusEnum;
import com.mashibing.internalcommon.dto.PriceRule;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.ForecastPriceDTO;
import com.mashibing.internalcommon.response.DirectionResponse;
import com.mashibing.internalcommon.response.ForecastPriceResponse;
import com.mashibing.internalcommon.utils.BigDecimalUtils;
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
public class PriceService {

	@Autowired
	private ServiceMapClient serviceMapClient;

	@Autowired
	private PriceRuleMapper priceRuleMapper;

	/**
	 * 计算预估价格
	 *
	 * @param depLongitude
	 * @param depLatitude
	 * @param destLongitude
	 * @param destLatitude
	 * @param cityCode
	 * @param vehicleType
	 * @return
	 */
	public ResponseResult<ForecastPriceResponse> forecastPrice(String depLongitude, String depLatitude, String destLongitude, String destLatitude,
	                                                           String cityCode, String vehicleType) {
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
		forecastPriceDTO.setCityCode(cityCode);
		forecastPriceDTO.setVehicleType(vehicleType);
		ResponseResult<DirectionResponse> directionResponse = serviceMapClient.directionDriving(forecastPriceDTO);
		Integer distance = directionResponse.getData().getDistance();
		Integer duration = directionResponse.getData().getDuration();
		log.info("距离：" + distance + "，时长：" + duration);

		//调用第三方接口API
		log.info("读取最新的计价规则");
		QueryWrapper<PriceRule> priceRuleQueryWrapper = new QueryWrapper<>();
		//city_code和vehicle_type组成联合索引
		priceRuleQueryWrapper.eq("city_code", cityCode);
		priceRuleQueryWrapper.eq("vehicle_type", vehicleType);
		priceRuleQueryWrapper.orderByDesc("fare_version");
		List<PriceRule> priceRules = priceRuleMapper.selectList(priceRuleQueryWrapper);
		//每一个城市的计价规则应该都只有一个
		if (priceRules.size() == 0) {
			return ResponseResult.fail(CommonStatusEnum.PRICE_RULE_NOT_EXISTS.getCode(), CommonStatusEnum.PRICE_RULE_NOT_EXISTS.getMessage());
		}
		PriceRule priceRule = priceRules.get(0);

		log.info("根据距离、时长、计价规则，计算价格");
		double price = getPrice(distance, duration, priceRule);

		ForecastPriceResponse forecastPriceResponse = new ForecastPriceResponse();
		forecastPriceResponse.setPrice(price);
		forecastPriceResponse.setCityCode(cityCode);
		forecastPriceResponse.setVehicleType(vehicleType);
		forecastPriceResponse.setFareType(priceRule.getFareType());
		forecastPriceResponse.setFareVersion(priceRule.getFareVersion());
		return ResponseResult.success(forecastPriceResponse);
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
		double price = 0.0;

		//起步价
		price = BigDecimalUtils.add(price, priceRule.getStartFare());

		//里程费
		//起步里程：公里
		//需要计费的里程 = 总里程（米转换成公里） - 起步里程（公里）
		;
		double subtract = BigDecimalUtils.subtract(BigDecimalUtils.divide(distance, 1000), priceRule.getStartMile());
		//只有需要计算的里程超过起步里程，才需要计价，否则不计价
		double sub = subtract < 0 ? 0 : subtract;

		//计程单价
		//里程费 = 计程单价 * 需要计费的里程
		double mileFarePrice = BigDecimalUtils.multiply(priceRule.getUnitPricePreMile(), sub);
		price = BigDecimalUtils.add(price, mileFarePrice);

		//时长费
		//时长：秒转换成分钟，并且保留小数点后两位，四舍五入
		double duration_minute = BigDecimalUtils.divide(duration, 60);
		//计时单价
		//时长费 = 时长 * 计时单价
		double durationPrice = BigDecimalUtils.multiply(priceRule.getUnitPricePreMinute(), duration_minute);
		price = BigDecimalUtils.add(price, durationPrice);

		price = BigDecimal.valueOf(price).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		return price;
	}

	/**
	 * 计算实际价格
	 *
	 * @param distance
	 * @param duration
	 * @param cityCode
	 * @param vehicleType
	 */
	public ResponseResult<Double> calculatePrice(Integer distance, Integer duration, String cityCode, String vehicleType) {
		QueryWrapper<PriceRule> priceRuleQueryWrapper = new QueryWrapper<>();
		//city_code和vehicle_type组成联合索引
		priceRuleQueryWrapper.eq("city_code", cityCode);
		priceRuleQueryWrapper.eq("vehicle_type", vehicleType);
		priceRuleQueryWrapper.orderByDesc("fare_version");
		List<PriceRule> priceRules = priceRuleMapper.selectList(priceRuleQueryWrapper);
		//每一个城市的计价规则应该都只有一个
		if (priceRules.size() == 0) {
			return ResponseResult.fail(CommonStatusEnum.PRICE_RULE_NOT_EXISTS.getCode(), CommonStatusEnum.PRICE_RULE_NOT_EXISTS.getMessage());
		}
		PriceRule priceRule = priceRules.get(0);

		log.info("根据距离、时长、计价规则，计算价格");
		double price = getPrice(distance, duration, priceRule);

		return ResponseResult.success(price);
	}

	/*public static void main(String[] args) {
		PriceRule priceRule = new PriceRule("110000", "1",10.0,3,1.8,0.5);

		System.out.println(getPrice(6500, 1800, priceRule));
	}*/
}
