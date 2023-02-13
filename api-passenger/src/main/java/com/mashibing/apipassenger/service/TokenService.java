package com.mashibing.apipassenger.service;

import com.mashibing.internalcommon.constant.CommonStatusEnum;
import com.mashibing.internalcommon.constant.TokenTypeConstant;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.dto.TokenResult;
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
 * @date 2023/2/12 - 16:53
 */
@Service
public class TokenService {

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	public ResponseResult refreshToken(String refreshTokenSrc) {
		//1.解析refreshToken
		TokenResult tokenResult = JWTUtils.checkToken(refreshTokenSrc);
		if (tokenResult == null) {
			return ResponseResult.fail(CommonStatusEnum.TOKEN_ERROR.getCode(), CommonStatusEnum.TOKEN_ERROR.getMessage());
		}

		//2.获取Redis中的refreshToken
		String phone = tokenResult.getPhone();
		String identity = tokenResult.getIdentity();
		//生成refreshTokenKey
		String refreshTokenKey = RedisKeyUtils.generateTokenKey(phone, identity, TokenTypeConstant.REFRESH_TOKEN_TYPE);
		//根据refreshTokenKey获取refreshToken
		String refreshTokenRedis = stringRedisTemplate.opsForValue().get(refreshTokenKey);

		//3.校验传入的refreshToken和从Redis中获取的refreshToken
		if ((StringUtils.isBlank(refreshTokenRedis)) || (!refreshTokenSrc.trim().equals(refreshTokenRedis.trim()))) {
			return ResponseResult.fail(CommonStatusEnum.TOKEN_ERROR.getCode(), CommonStatusEnum.TOKEN_ERROR.getMessage());
		}

		String accessTokenKey = RedisKeyUtils.generateTokenKey(phone, identity, TokenTypeConstant.ACCESS_TOKEN_TYPE);

		//4.生成新的双token：accessToken和refreshToken
		String accessToken = JWTUtils.generatorToken(phone, identity, TokenTypeConstant.ACCESS_TOKEN_TYPE);
		String refreshToken = JWTUtils.generatorToken(phone, identity, TokenTypeConstant.REFRESH_TOKEN_TYPE);
		//将双token存入Redis
		stringRedisTemplate.opsForValue().set(accessTokenKey, accessToken, 30, TimeUnit.DAYS);
		stringRedisTemplate.opsForValue().set(refreshTokenKey, refreshToken, 31, TimeUnit.DAYS);

		//5.响应新的双token
		TokenResponse tokenResponse = new TokenResponse();
		tokenResponse.setAccessToken(accessToken);
		tokenResponse.setRefreshToken(refreshToken);
		return ResponseResult.success(tokenResponse);
	}
}
