package com.mashibing.apipassenger.service;

import com.mashibing.apipassenger.remote.ServicePassengerUserClient;
import com.mashibing.apipassenger.remote.ServiceVerificationcodeClient;
import com.mashibing.internalcommon.constant.CommonStatusEnum;
import com.mashibing.internalcommon.constant.IdentityConstant;
import com.mashibing.internalcommon.constant.TokenTypeConstant;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.VerificationcodeDTO;
import com.mashibing.internalcommon.response.NumberCodeResponse;
import com.mashibing.internalcommon.response.TokenResponse;
import com.mashibing.internalcommon.utils.JWTUtils;
import com.mashibing.internalcommon.utils.RedisKeyUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author xcy
 * @date 2023/2/6 - 9:39
 */
@Service
public class VerificationcodeService {
	@Autowired
	private ServiceVerificationcodeClient serviceVerificationcodeClient;

	@Autowired
	private ServicePassengerUserClient servicePassengerUserClient;

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	/**
	 * 生成验证码
	 *
	 * @param phone 手机号
	 * @return
	 */
	public ResponseResult generateVerificationcode(String phone) {
		System.out.println("乘客获取验证码，调用验证码服务");
		ResponseResult<NumberCodeResponse> numberCodeResponse = serviceVerificationcodeClient.getNumberCode(6);
		int numberCode = numberCodeResponse.getData().getNumberCode();
		System.out.println("远程调用的numberCode: " + numberCode);

		//存入Redis
		System.out.println("存入Redis");

		//key,value,ttl过期时间
		String key = RedisKeyUtils.generateVerificationcodeKey(phone, IdentityConstant.PASSENGER_IDENTITY);
		stringRedisTemplate.opsForValue().set(key, numberCode + "", 2, TimeUnit.MINUTES);

		//返回值
		return ResponseResult.success("");
	}


	/**
	 * 校验验证码
	 *
	 * @param phone            手机号
	 * @param verificationcode 验证码
	 * @return
	 */
	public ResponseResult checkVerificationcode(String phone, String verificationcode) {
		//key
		String VerificationcodeKey = RedisKeyUtils.generateVerificationcodeKey(phone, IdentityConstant.PASSENGER_IDENTITY);
		//value
		String redisVerificationcode = stringRedisTemplate.opsForValue().get(VerificationcodeKey);
		System.out.println(redisVerificationcode);
		//根据乘客手机号，从Redis中获取验证码
		//校验验证码
		if (StringUtils.isBlank(redisVerificationcode)) {
			return ResponseResult.fail(CommonStatusEnum.VERIFICATIONCODE_OVERDUE.getCode(), CommonStatusEnum.VERIFICATIONCODE_OVERDUE.getMessage());
		}

		if (!verificationcode.trim().equals(redisVerificationcode.trim())) {
			return ResponseResult.fail(CommonStatusEnum.VERIFICATIONCODE_ERROR.getCode(), CommonStatusEnum.VERIFICATIONCODE_ERROR.getMessage());
		}

		//根据不同的情况，做不同的处理
		VerificationcodeDTO verificationcodeDTO = new VerificationcodeDTO();
		verificationcodeDTO.setPassengerPhone(phone);
		servicePassengerUserClient.loginOrRegister(verificationcodeDTO);

		//颁发令牌
		String accessToken = JWTUtils.generatorToken(phone, IdentityConstant.PASSENGER_IDENTITY, TokenTypeConstant.ACCESS_TOKEN_TYPE);
		String refreshToken = JWTUtils.generatorToken(phone, IdentityConstant.PASSENGER_IDENTITY, TokenTypeConstant.REFRESH_TOKEN_TYPE);

		//将token存储到Redis中
		String accessTokenKey = RedisKeyUtils.generateTokenKey(phone, IdentityConstant.PASSENGER_IDENTITY, TokenTypeConstant.ACCESS_TOKEN_TYPE);
		String refreshTokenKey = RedisKeyUtils.generateTokenKey(phone, IdentityConstant.PASSENGER_IDENTITY, TokenTypeConstant.REFRESH_TOKEN_TYPE);
		stringRedisTemplate.opsForValue().set(accessTokenKey, accessToken, 30, TimeUnit.DAYS);
		stringRedisTemplate.opsForValue().set(refreshTokenKey, refreshToken, 31, TimeUnit.DAYS);

		//响应结果，返回token令牌
		TokenResponse tokenResponse = new TokenResponse();
		tokenResponse.setAccessToken(accessToken);
		tokenResponse.setRefreshToken(refreshToken);
		return ResponseResult.success(tokenResponse);
	}
}
