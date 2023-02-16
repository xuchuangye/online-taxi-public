package com.mashibing.servicedriveruser.service;

import com.mashibing.internalcommon.dto.DriverUser;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.servicedriveruser.mapper.DriverUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @author xcy
 * @date 2023/2/16 - 10:53
 */
@Service
public class DriverUserService {

	@Autowired
	private DriverUserMapper driverUserMapper;

	/**
	 * 插入司机信息
	 * @param driverUser
	 * @return
	 */
	public ResponseResult addDriverUser(DriverUser driverUser) {
		LocalDateTime now = LocalDateTime.now();
		//创建时间
		driverUser.setGmtCreate(now);
		//更新时间
		driverUser.setGmtModified(now);
		driverUserMapper.insert(driverUser);
		return ResponseResult.success("");
	}

	/**
	 * 修改司机信息
	 * @param driverUser
	 * @return
	 */
	public ResponseResult updateDriverUser(DriverUser driverUser) {
		LocalDateTime now = LocalDateTime.now();
		//更新时间
		driverUser.setGmtModified(now);
		driverUserMapper.updateById(driverUser);
		return ResponseResult.success("");
	}
}
