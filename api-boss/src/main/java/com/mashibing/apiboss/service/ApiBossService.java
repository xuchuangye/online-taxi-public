package com.mashibing.apiboss.service;

import com.mashibing.apiboss.remote.ServiceDriverUserClient;
import com.mashibing.internalcommon.dto.DriverUser;
import com.mashibing.internalcommon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xcy
 * @date 2023/2/16 - 15:35
 */
@Service
public class ApiBossService {

	@Autowired
	private ServiceDriverUserClient serviceDriverUserClient;

	public ResponseResult addDriverUser(DriverUser driverUser) {
		return serviceDriverUserClient.addDriverUser(driverUser);
	}
}
