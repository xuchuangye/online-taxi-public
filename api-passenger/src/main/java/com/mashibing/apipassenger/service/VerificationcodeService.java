package com.mashibing.apipassenger.service;

import ch.qos.logback.core.util.TimeUtil;
import com.mashibing.apipassenger.remote.ServiceVerificationcodeClient;
import com.mashibing.internalcommon.constant.CommonStatusEnum;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.NumberCodeResponse;
import com.mashibing.internalcommon.response.TokenResponse;
import net.sf.json.JSONObject;
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
	private StringRedisTemplate stringRedisTemplate;

	//验证码的前缀
	private String verificationcodePrefix = "passenger-verificationcode-";

	public ResponseResult generateVerificationcode(String passengerPhone) {
		System.out.println("乘客获取验证码，调用验证码服务");
		ResponseResult<NumberCodeResponse> numberCodeResponse = serviceVerificationcodeClient.getNumberCode(6);
		int numberCode = numberCodeResponse.getData().getNumberCode();
		System.out.println("远程调用的numberCode: " + numberCode);

		//存入Redis
		System.out.println("存入Redis");

		//key,value,ttl过期时间
		String key = redisKey(passengerPhone);
		stringRedisTemplate.opsForValue().set(key, numberCode + "", 2, TimeUnit.MINUTES);

		//返回值

		return ResponseResult.success("");
	}

	private String redisKey(String passengerPhone) {
		return verificationcodePrefix + passengerPhone;
	}

	public ResponseResult checkVerificationcode(String passengerPhone, String verificationcode) {
		//key
		String key = redisKey(passengerPhone);
		//value
		String redisVerificationcode = stringRedisTemplate.opsForValue().get(key);
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

		//响应结果，返回token令牌
		TokenResponse tokenResponse = new TokenResponse();
		tokenResponse.setToken("token value");
		return ResponseResult.success(tokenResponse);
	}
}
