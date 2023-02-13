package com.mashibing.servicepassengeruser.service;

import com.mashibing.internalcommon.constant.CommonStatusEnum;
import com.mashibing.internalcommon.dto.PassengerUser;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.servicepassengeruser.mapper.PassengerUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xcy
 * @date 2023/2/11 - 10:10
 */
@Service
public class PassengerUserService {

	@Autowired
	private PassengerUserMapper passengerUserMapper;


	public ResponseResult loginOrRegister(String passengerPhone) {
		System.out.println("获取到的手机号：" + passengerPhone);

		//根据手机号获取用户信息
		Map<String, Object> map = new HashMap<>();
		map.put("passenger_Phone", passengerPhone);
		List<PassengerUser> passengerUsers = passengerUserMapper.selectByMap(map);

		//判断用户信息是否存在
		if (passengerUsers == null || passengerUsers.size() == 0) {
			System.out.println("根据手机号获取的用户信息为空");

			//如果用户信息不存在，插入用户
			PassengerUser passengerUser = new PassengerUser();
			passengerUser.setPassengerName("李四");
			passengerUser.setPassengerGender((byte) 1);
			passengerUser.setPassengerPhone(passengerPhone);
			LocalDateTime now = LocalDateTime.now();
			passengerUser.setGmtCreate(now);
			passengerUser.setGmtModified(now);
			passengerUserMapper.insert(passengerUser);
		}
		//如果用户信息存在，查询用户
		return ResponseResult.success("");
	}

	public ResponseResult getUserByPassengerPhone(String passengerPhone) {
		//根据手机号获取用户信息
		Map<String, Object> map = new HashMap<>();
		map.put("passenger_Phone", passengerPhone);
		List<PassengerUser> passengerUsers = passengerUserMapper.selectByMap(map);

		//判断用户信息是否存在
		if (passengerUsers == null || passengerUsers.size() == 0) {
			return ResponseResult.fail(CommonStatusEnum.USER_IS_NOT_EXISTS.getCode(), CommonStatusEnum.USER_IS_NOT_EXISTS.getMessage());
		} else {
			PassengerUser passengerUser = passengerUsers.get(0);
			return ResponseResult.success(passengerUser);
		}
	}
}
