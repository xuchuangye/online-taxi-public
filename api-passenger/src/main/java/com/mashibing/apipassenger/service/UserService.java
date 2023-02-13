package com.mashibing.apipassenger.service;

import com.mashibing.internalcommon.constant.CommonStatusEnum;
import com.mashibing.internalcommon.dto.PassengerUser;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.dto.TokenResult;
import com.mashibing.internalcommon.utils.JWTUtils;
import org.springframework.stereotype.Service;

/**
 * @author xcy
 * @date 2023/2/13 - 9:15
 */
@Service
public class UserService {

	public ResponseResult getUsersByAccessToken(String accessToken) {
		System.out.println("获取的accessToken: " + accessToken);
		//解析token，得到手机号
		TokenResult tokenResult = JWTUtils.checkToken(accessToken);
		if (tokenResult == null) {
			return ResponseResult.fail(CommonStatusEnum.TOKEN_ERROR.getCode(), CommonStatusEnum.TOKEN_ERROR.getMessage());
		}
		//根据手机号查询用户信息
		String phone = tokenResult.getPhone();

		//
		PassengerUser passengerUser = new PassengerUser();
		passengerUser.setPassengerName("张三");
		passengerUser.setProfilePhoto("工作照");
		return ResponseResult.success(passengerUser);
	}
}
