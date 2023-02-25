package com.mashibing.apidriver.service;

import com.mashibing.apidriver.remote.ServiceDriverUserClient;
import com.mashibing.apidriver.remote.ServiceVerificationcodeClient;
import com.mashibing.internalcommon.constant.CommonStatusEnum;
import com.mashibing.internalcommon.dto.DriverCarBindingRelationship;
import com.mashibing.internalcommon.dto.DriverUser;
import com.mashibing.internalcommon.dto.DriverUserWorkStatus;
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

	/**
	 * 修改司机工作状态
	 * @param driverUserWorkStatus
	 * @return
	 */
	public ResponseResult changeWorkStatus(DriverUserWorkStatus driverUserWorkStatus) {
		return serviceDriverUserClient.changeWorkStatus(driverUserWorkStatus);
	}

	/**
	 * 根据解析token获取的司机手机号，查询对应的司机id，再根据司机id获取司机和车辆的对应关系
	 * @param driverPhone
	 * @return
	 */
	public ResponseResult<DriverCarBindingRelationship> getDriverCarBindingRelationship(String driverPhone) {
		return serviceDriverUserClient.getDriverCarBindingRelationship(driverPhone);
	}
}
