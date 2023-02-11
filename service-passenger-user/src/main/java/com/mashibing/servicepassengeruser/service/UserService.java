package com.mashibing.servicepassengeruser.service;

import com.mashibing.internalcommon.dto.ResponseResult;
import org.springframework.stereotype.Service;

/**
 * @author xcy
 * @date 2023/2/11 - 10:10
 */
@Service
public class UserService {


	public ResponseResult loginOrRegister(String passengerPhone) {
		System.out.println("获取到的手机号：" + passengerPhone);

		//根据手机号获取用户信息

		//判断用户信息是否存在

		//如果用户信息不存在，插入用户

		//如果用户信息存在，查询用户

		return ResponseResult.success("");
	}
}
