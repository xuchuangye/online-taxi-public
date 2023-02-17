package com.mashibing.apidriver.service;

import com.mashibing.apidriver.remote.ServiceDriverUserClient;
import com.mashibing.apidriver.remote.ServiceVerificationcodeClient;
import com.mashibing.internalcommon.constant.CommonStatusEnum;
import com.mashibing.internalcommon.dto.DriverUser;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.DriverUserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xcy
 * @date 2023/2/16 - 20:12
 */
@Service
public class DriverUserService {

	@Autowired
	private ServiceDriverUserClient serviceDriverUserClient;

	/**
	 * 维护司机信息
	 *
	 * @param driverUser
	 * @return
	 */
	public ResponseResult updateUser(DriverUser driverUser) {
		return serviceDriverUserClient.updateDriverUser(driverUser);
	}
}
