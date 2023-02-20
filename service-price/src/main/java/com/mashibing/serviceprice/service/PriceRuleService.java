package com.mashibing.serviceprice.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mashibing.internalcommon.dto.PriceRule;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.serviceprice.mapper.PriceRuleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xuchuangye
 * @since 2023-02-20
 */
@Service
public class PriceRuleService {

	@Autowired
	private PriceRuleMapper priceRuleMapper;

	public ResponseResult addPriceRule(PriceRule priceRule) {
		//拼接fareType
		String cityCode = priceRule.getCityCode();
		String vehicleType = priceRule.getVehicleType();
		String fareType = cityCode + vehicleType;
		priceRule.setFareType(fareType);

		//设置版本号
		//问题1：因为增加 了版本号，所以cityCode和vehicleType这两个字段无法确定唯一记录
		//问题2：因为需要找到最大的版本号，所以需要进行逆序排序
		QueryWrapper<PriceRule> priceRuleQueryWrapper = new QueryWrapper<>();
		priceRuleQueryWrapper.eq("city_code", cityCode);
		priceRuleQueryWrapper.eq("vehicle_type", vehicleType);
		priceRuleQueryWrapper.orderByDesc("fare_version");

		List<PriceRule> priceRules = priceRuleMapper.selectList(priceRuleQueryWrapper);

		Integer fareVersion = 0;
		if (priceRules.size() != 0) {
			fareVersion = priceRules.get(0).getFareVersion();
		}
		priceRule.setFareVersion(++fareVersion);
		priceRuleMapper.insert(priceRule);
		return ResponseResult.success("");
	}
}
