package com.mashibing.serviceprice.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mashibing.internalcommon.constant.CommonStatusEnum;
import com.mashibing.internalcommon.dto.PriceRule;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.serviceprice.mapper.PriceRuleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author xuchuangye
 * @since 2023-02-20
 */
@Service
public class PriceRuleService {

	@Autowired
	private PriceRuleMapper priceRuleMapper;

	/**
	 * 添加计价规则
	 *
	 * @param priceRule 计价规则实体类
	 * @return
	 */
	public ResponseResult addPriceRule(PriceRule priceRule) {
		//拼接fareType
		String cityCode = priceRule.getCityCode();
		String vehicleType = priceRule.getVehicleType();
		String fareType = cityCode + "$" + vehicleType;
		priceRule.setFareType(fareType);

		//设置版本号
		//问题1：因为增加 了版本号，所以cityCode和vehicleType这两个字段无法确定唯一记录
		//问题2：因为需要找到最大的版本号，所以需要进行逆序排序
		QueryWrapper<PriceRule> priceRuleQueryWrapper = new QueryWrapper<>();
		priceRuleQueryWrapper.eq("city_code", cityCode);
		priceRuleQueryWrapper.eq("vehicle_type", vehicleType);
		priceRuleQueryWrapper.orderByDesc("fare_version");

		//根据cityCode和vehicleType查询记录
		List<PriceRule> priceRules = priceRuleMapper.selectList(priceRuleQueryWrapper);

		//如果查询出记录，表示计价规则已存在
		int fareVersion = 0;
		if (priceRules.size() != 0) {
			return ResponseResult.fail(CommonStatusEnum.PRICE_RULE_EXISTS.getCode(), CommonStatusEnum.PRICE_RULE_EXISTS.getMessage());
		}

		priceRule.setFareVersion(++fareVersion);
		priceRuleMapper.insert(priceRule);
		return ResponseResult.success("");
	}

	/**
	 * 修改计价规则
	 *
	 * @param priceRule 计价规则实体类
	 * @return
	 */
	public ResponseResult updatePriceRule(PriceRule priceRule) {
		//拼接fareType
		String cityCode = priceRule.getCityCode();
		String vehicleType = priceRule.getVehicleType();
		String fareType = cityCode + "$" + vehicleType;
		priceRule.setFareType(fareType);

		//设置版本号
		//问题1：因为增加 了版本号，所以cityCode和vehicleType这两个字段无法确定唯一记录
		//问题2：因为需要找到最大的版本号，所以需要进行逆序排序
		QueryWrapper<PriceRule> priceRuleQueryWrapper = new QueryWrapper<>();
		priceRuleQueryWrapper.eq("city_code", cityCode);
		priceRuleQueryWrapper.eq("vehicle_type", vehicleType);
		priceRuleQueryWrapper.orderByDesc("fare_version");

		//根据cityCode和vehicleType查询记录
		List<PriceRule> priceRules = priceRuleMapper.selectList(priceRuleQueryWrapper);

		//如果查询出记录，那么就比较计价规则
		Integer fareVersion = 0;
		if (priceRules.size() != 0) {
			PriceRule priceRuleDB = priceRules.get(0);
			//如果没有变化，那么直接返回
			if (priceRule.getUnitPricePreMile().doubleValue() == priceRuleDB.getUnitPricePreMile().doubleValue()
					&& priceRule.getUnitPricePreMinute().doubleValue() == priceRuleDB.getUnitPricePreMinute().doubleValue()
					&& priceRule.getStartFare().doubleValue() == priceRuleDB.getStartFare().doubleValue()
					&& priceRule.getStartMile().doubleValue() == priceRuleDB.getStartMile().doubleValue()) {
				return ResponseResult.fail(CommonStatusEnum.PRICE_RULE_NOT_CHANGE.getCode(),
						CommonStatusEnum.PRICE_RULE_NOT_CHANGE.getMessage());
			}
			fareVersion = priceRuleDB.getFareVersion();
		}
		priceRule.setFareVersion(++fareVersion);
		priceRuleMapper.insert(priceRule);
		return ResponseResult.success("");
	}

	/**
	 * 获取最新版本的计价规则
	 *
	 * @param fareType
	 * @return
	 */
	public ResponseResult<PriceRule> getNewestVersion(String fareType) {
		QueryWrapper<PriceRule> priceRuleQueryWrapper = new QueryWrapper<>();
		priceRuleQueryWrapper.eq("fare_type", fareType);
		//获取最新的，也就是fare_version是最大的，所以进行逆序排序
		priceRuleQueryWrapper.orderByDesc("fare_version");

		List<PriceRule> priceRules = priceRuleMapper.selectList(priceRuleQueryWrapper);
		if (priceRules == null || priceRules.size() == 0) {
			return ResponseResult.fail(CommonStatusEnum.PRICE_RULE_NOT_EXISTS.getCode(),
					CommonStatusEnum.PRICE_RULE_NOT_EXISTS.getMessage());
		} else {
			return ResponseResult.success(priceRules.get(0));
		}
	}

	/**
	 * 判断当前版本的计价规则是否是最新的
	 *
	 * @param fareType
	 * @param fareVersion
	 * @return
	 */
	public ResponseResult<Boolean> isNewestVersion(String fareType, Integer fareVersion) {
		ResponseResult<PriceRule> priceRuleResponseResult = getNewestVersion(fareType);
		if (priceRuleResponseResult.getCode() == CommonStatusEnum.PRICE_RULE_NOT_EXISTS.getCode()) {
			return ResponseResult.fail(CommonStatusEnum.PRICE_RULE_NOT_EXISTS.getCode(),
					CommonStatusEnum.PRICE_RULE_NOT_EXISTS.getMessage());
		}
		PriceRule priceRuleDB = priceRuleResponseResult.getData();
		if (priceRuleDB.getFareVersion() > fareVersion) {
			return ResponseResult.success(false);
		}else {
			return ResponseResult.success(true);
		}
	}
}
