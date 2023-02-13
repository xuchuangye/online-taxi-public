package com.mashibing.apipassenger.service;

import com.mashibing.apipassenger.remote.ServicePassengerUserClient;
import com.mashibing.internalcommon.constant.CommonStatusEnum;
import com.mashibing.internalcommon.dto.PassengerUser;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.dto.TokenResult;
import com.mashibing.internalcommon.request.VerificationcodeDTO;
import com.mashibing.internalcommon.utils.JWTUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xcy
 * @date 2023/2/13 - 9:15
 */
@Service
@Slf4j
public class UserService {
	@Autowired
	private ServicePassengerUserClient servicePassengerUserClient;

	public ResponseResult getUsersByAccessToken(String accessToken) {
		log.info("获取的accessToken: " + accessToken);
		//解析token，得到手机号
		TokenResult tokenResult = JWTUtils.checkToken(accessToken);
		if (tokenResult == null) {
			return ResponseResult.fail(CommonStatusEnum.TOKEN_ERROR.getCode(), CommonStatusEnum.TOKEN_ERROR.getMessage());
		}
		//根据手机号查询用户信息
		String phone = tokenResult.getPhone();
		log.info("手机号: " + phone);
		//
		/*VerificationcodeDTO verificationcodeDTO = new VerificationcodeDTO();
		verificationcodeDTO.setPassengerPhone(phone);*/
		return ResponseResult.success(servicePassengerUserClient.getUser(phone).getData());
	}
}
