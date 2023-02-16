package com.mashibing.servicedriveruser.service;

import com.mashibing.internalcommon.dto.DriverUser;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.servicedriveruser.mapper.DriverUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xcy
 * @date 2023/2/16 - 10:53
 */
@Service
public class DriverUserService {

	@Autowired
	private DriverUserMapper driverUserMapper;

	public ResponseResult testGetDriverUser() {
		DriverUser driverUser = driverUserMapper.selectById(1566711039841685505L);
		return ResponseResult.success(driverUser);
	}
}
