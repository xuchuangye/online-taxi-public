package com.mashibing.servicemap.service;

import com.mashibing.internalcommon.constant.CommonStatusEnum;
import com.mashibing.internalcommon.constant.MapConfigConstant;
import com.mashibing.internalcommon.dto.DicDistrict;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.servicemap.mapper.DicDistrictMapper;
import com.mashibing.servicemap.remote.MapDicDistrictClient;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xcy
 * @date 2023/2/15 - 11:00
 */
@Service
public class DicDistrictService {

	@Autowired
	private MapDicDistrictClient mapDicDistrictClient;

	@Autowired
	private DicDistrictMapper dicDistrictMapper;

	public ResponseResult initDicDistrict(String keywords) {

		String dicDistrictString = mapDicDistrictClient.dicDistrict(keywords);
		System.out.println(dicDistrictString);
		//解析url
		//最外层的JSON对象
		JSONObject jsonObject = JSONObject.fromObject(dicDistrictString);
		if (jsonObject.has(MapConfigConstant.STATUS)) {
			//获取并查看状态
			int status = jsonObject.getInt(MapConfigConstant.STATUS);
			//status != 1表示拉取地图地区字典信息失败
			if (status != 1) {
				return ResponseResult.fail(CommonStatusEnum.DISTRICT_URL_ERROR.getCode(), CommonStatusEnum.DISTRICT_URL_ERROR.getMessage());
			}
			if (jsonObject.has(MapConfigConstant.DISTRICTS)) {
				//国家级
				JSONArray countrysArray = jsonObject.getJSONArray(MapConfigConstant.DISTRICTS);
				for (int i = 0; i < countrysArray.size(); i++) {
					JSONObject countryObject = countrysArray.getJSONObject(i);
					String countryAddressCode = countryObject.getString(MapConfigConstant.ADCODE);
					String countryAddressName = countryObject.getString(MapConfigConstant.NAME);
					String countryParentAddressCode = "0";
					String countryLevel = countryObject.getString(MapConfigConstant.LEVEL);
					insert(countryAddressCode, countryAddressName, countryParentAddressCode, countryLevel);
					//省级
					JSONArray provincesArray = countryObject.getJSONArray(MapConfigConstant.DISTRICTS);
					for (int j = 0; j < provincesArray.size(); j++) {
						JSONObject provinceObject = provincesArray.getJSONObject(j);
						String provinceAddressCode = provinceObject.getString(MapConfigConstant.ADCODE);
						String provinceAddressName = provinceObject.getString(MapConfigConstant.NAME);
						String provinceParentAddressCode = countryAddressCode;
						String provinceLevel = provinceObject.getString(MapConfigConstant.LEVEL);
						insert(provinceAddressCode, provinceAddressName, provinceParentAddressCode, provinceLevel);

						//市级
						JSONArray citysArray = provinceObject.getJSONArray(MapConfigConstant.DISTRICTS);
						for (int k = 0; k < citysArray.size(); k++) {
							JSONObject cityObject = citysArray.getJSONObject(k);
							String cityAddressCode = cityObject.getString(MapConfigConstant.ADCODE);
							String cityAddressName = cityObject.getString(MapConfigConstant.NAME);
							String cityParentAddressCode = provinceAddressCode;
							String cityLevel = cityObject.getString(MapConfigConstant.LEVEL);
							insert(cityAddressCode, cityAddressName, cityParentAddressCode, cityLevel);
							//地区级
							JSONArray districtsArray = cityObject.getJSONArray(MapConfigConstant.DISTRICTS);
							for (int l = 0; l < districtsArray.size(); l++) {
								JSONObject districtObject = districtsArray.getJSONObject(l);
								String districtAddressCode = districtObject.getString(MapConfigConstant.ADCODE);
								String districtAddressName = districtObject.getString(MapConfigConstant.NAME);
								String districtParentAddressCode = cityAddressCode;
								String districtLevel = districtObject.getString(MapConfigConstant.LEVEL);

								if(districtLevel.equals(MapConfigConstant.STREET)) {
									continue;
								}

								insert(districtAddressCode, districtAddressName, districtParentAddressCode, districtLevel);
							}
						}
					}
				}
			}
		}
		//插入到数据库

		return ResponseResult.success("");
	}

	public void insert(String addressCode, String addressName, String parentAddressCode, String level) {
		//
		DicDistrict dicDistrict = new DicDistrict();
		dicDistrict.setAddressCode(addressCode);
		dicDistrict.setAddressName(addressName);
		dicDistrict.setParentAddressCode(parentAddressCode);
		dicDistrict.setLevel(levelStringToInt(level));
		//插入到数据库
		dicDistrictMapper.insert(dicDistrict);
	}

	/**
	 * 根据level对应的字符串级别，生成对应的数字，方便设置到数据库对象中
	 *
	 * @param levelString 字符串表示的级别：国家、省、市、县
	 * @return 返回级别对应的数字
	 */
	public int levelStringToInt(String levelString) {
		int level = 0;
		switch (levelString.trim()) {
			case "country":
				level = 0;
				break;
			case "province":
				level = 1;
				break;
			case "city":
				level = 2;
				break;
			case "district":
				level = 3;
		}
		return level;
	}
}
