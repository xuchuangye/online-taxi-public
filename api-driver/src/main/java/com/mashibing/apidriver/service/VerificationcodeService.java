package com.mashibing.apidriver.service;

import com.mashibing.apidriver.remote.ServiceDriverUserClient;
import com.mashibing.apidriver.remote.ServiceVerificationcodeClient;
import com.mashibing.internalcommon.constant.CommonStatusEnum;
import com.mashibing.internalcommon.dto.DriverUser;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.VerificationcodeDTO;
import com.mashibing.internalcommon.response.DriverUserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xcy
 * @date 2023/2/18 - 7:16
 */
@Service
public class VerificationcodeService {
	@Autowired
	private ServiceDriverUserClient serviceDriverUserClient;

	@Autowired
	private ServiceVerificationcodeClient serviceVerificationcodeClient;

	/**
	 * 查询司机信息
	 *
	 * @param driverPhone
	 * @return
	 */
	public ResponseResult checkAndSendVerificationcode(String driverPhone) {
		/*//远程调用service-driver-user，从数据库中查询司机信息
		ResponseResult<DriverUserResponse> driverUserResponseResponseResult = serviceDriverUserClient.selectDriverUser(driverPhone);

		String driverPhoneData = driverUserResponseResponseResult.getData().getDriverPhone();
		Integer isExists = driverUserResponseResponseResult.getData().getIsExists();

		//如果司机不存在，返回提示信息
		if (isExists == 0) {
			return ResponseResult.fail(CommonStatusEnum.DRIVER_NOT_EXISTS.getCode(),
					CommonStatusEnum.DRIVER_NOT_EXISTS.getMessage());
		} else {
			//如果司机用户存在，远程调用service-verificationcode进行获取验证码
			serviceVerificationcodeClient.numberCode(6);

			//存入到Redis

		}*/
		return ResponseResult.success("");
	}
}
