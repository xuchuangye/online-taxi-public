package com.mashibing.apidriver.service;

import com.mashibing.apidriver.remote.ServiceDriverUserClient;
import com.mashibing.apidriver.remote.ServiceVerificationcodeClient;
import com.mashibing.internalcommon.constant.CommonStatusEnum;
import com.mashibing.internalcommon.constant.DriverConstant;
import com.mashibing.internalcommon.constant.IdentityConstant;
import com.mashibing.internalcommon.constant.TokenTypeConstant;
import com.mashibing.internalcommon.dto.DriverUser;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.VerificationcodeDTO;
import com.mashibing.internalcommon.response.DriverUserResponse;
import com.mashibing.internalcommon.response.NumberCodeResponse;
import com.mashibing.internalcommon.response.TokenResponse;
import com.mashibing.internalcommon.utils.JWTUtils;
import com.mashibing.internalcommon.utils.RedisKeyUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author xcy
 * @date 2023/2/18 - 7:16
 */
@Service
@Slf4j
public class VerificationcodeService {
	@Autowired
	private ServiceDriverUserClient serviceDriverUserClient;

	@Autowired
	private ServiceVerificationcodeClient serviceVerificationcodeClient;

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	/**
	 * 查询司机信息
	 *
	 * @param driverPhone
	 * @return
	 */
	public ResponseResult checkAndSendVerificationcode(String driverPhone) {
		//远程调用service-driver-user，从数据库中查询司机信息
		ResponseResult<DriverUserResponse> driverUserResponseResponseResult = serviceDriverUserClient.selectDriverUser(driverPhone);

		Integer isExists = driverUserResponseResponseResult.getData().getIsExists();

		//如果司机不存在，返回提示信息
		if (isExists == DriverConstant.DRIVER_NOT_EXISTS) {
			return ResponseResult.fail(CommonStatusEnum.DRIVER_NOT_EXISTS.getCode(),
					CommonStatusEnum.DRIVER_NOT_EXISTS.getMessage());
		}
		//log.info(driverPhone + "：司机存在");
		else {
			//如果司机用户存在，远程调用service-verificationcode进行获取验证码
			ResponseResult<NumberCodeResponse> numberCodeResponseResponseResult = serviceVerificationcodeClient.numberCode(6);

			int numberCode = numberCodeResponseResponseResult.getData().getNumberCode();
			log.info("验证码：" + numberCode);

			//调用第三方发送验证码

			//存入到Redis
			String key = RedisKeyUtils.generateVerificationcodeKey(driverPhone, IdentityConstant.DRIVER_IDENTITY);
			stringRedisTemplate.opsForValue().set(key, numberCode + "", 2, TimeUnit.MINUTES);

			return ResponseResult.success("");
		}
	}

	/**
	 * 校验验证码
	 *
	 * @param driverPhone            手机号
	 * @param verificationcode 验证码
	 * @return
	 */
	public ResponseResult checkVerificationcode(String driverPhone, String verificationcode) {
		//key
		String VerificationcodeKey = RedisKeyUtils.generateVerificationcodeKey(driverPhone, IdentityConstant.DRIVER_IDENTITY);
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

		//颁发令牌
		String accessToken = JWTUtils.generatorToken(driverPhone, IdentityConstant.DRIVER_IDENTITY, TokenTypeConstant.ACCESS_TOKEN_TYPE);
		String refreshToken = JWTUtils.generatorToken(driverPhone, IdentityConstant.DRIVER_IDENTITY, TokenTypeConstant.REFRESH_TOKEN_TYPE);

		//将token存储到Redis中
		String accessTokenKey = RedisKeyUtils.generateTokenKey(driverPhone, IdentityConstant.DRIVER_IDENTITY, TokenTypeConstant.ACCESS_TOKEN_TYPE);
		String refreshTokenKey = RedisKeyUtils.generateTokenKey(driverPhone, IdentityConstant.DRIVER_IDENTITY, TokenTypeConstant.REFRESH_TOKEN_TYPE);
		stringRedisTemplate.opsForValue().set(accessTokenKey, accessToken, 30, TimeUnit.DAYS);
		stringRedisTemplate.opsForValue().set(refreshTokenKey, refreshToken, 31, TimeUnit.DAYS);

		//响应结果，返回token令牌
		TokenResponse tokenResponse = new TokenResponse();
		tokenResponse.setAccessToken(accessToken);
		tokenResponse.setRefreshToken(refreshToken);
		return ResponseResult.success(tokenResponse);
	}
}
