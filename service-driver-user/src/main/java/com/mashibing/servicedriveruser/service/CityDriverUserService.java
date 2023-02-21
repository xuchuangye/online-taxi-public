package com.mashibing.servicedriveruser.service;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.servicedriveruser.mapper.DriverUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author xcy
 * @date 2023/2/21 - 16:14
 */
@Service
public class CityDriverUserService {

	@Autowired
	private DriverUserMapper driverUserMapper;

	/**
	 * 根据城市编码判断当前城市是否有可用的司机
	 *
	 * @param cityCode
	 * @return
	 */
	public ResponseResult<Boolean> isAvailableDriver(String cityCode) {
		Integer count = driverUserMapper.selectDriverUserCountByCityCode(cityCode);
		if (count > 0) {
			return ResponseResult.success(true);
		} else {
			return ResponseResult.success(false);
		}
	}
}
