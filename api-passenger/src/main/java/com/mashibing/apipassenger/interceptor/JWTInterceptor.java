package com.mashibing.apipassenger.interceptor;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.dto.TokenResult;
import com.mashibing.internalcommon.utils.JWTUtils;
import com.mashibing.internalcommon.utils.RedisKeyUtils;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * 该类需要在配置类中进行注册，否则在该类中注入的属性无效
 * @author xcy
 * @date 2023/2/12 - 9:56
 */
public class JWTInterceptor implements HandlerInterceptor {

	//如果在没有标注@Component注解的类中注入属性的话，需要该类在配置类中进行注册
	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		//是否拦截，True表示是，false表示否
		boolean result = true;
		//处理并捕获异常的提示信息
		String resultString = "";
		//从Header的授权中获取token
		String token = request.getHeader("Authorization");
		//解析token
		TokenResult tokenResult = null;
		try {
			tokenResult = JWTUtils.parseToken(token);
		}
		//签名错误
		catch (SignatureVerificationException e) {
			resultString = "token sign error";
			result = false;
		}
		//token过期
		catch (TokenExpiredException e) {
			resultString = "token time out";
			result = false;
		}
		//token算法错误
		catch (AlgorithmMismatchException e) {
			resultString = "token AlgorithmMismatchException";
			result = false;
		} catch (Exception e) {
			resultString = "token invalid";
			result = false;
		}

		if (tokenResult == null) {
			resultString = "token invalid";
			result = false;
		} else {
			String phone = tokenResult.getPhone();
			String identity = tokenResult.getIdentity();
			//根据手机号和身份标识生成token
			String tokenKey = RedisKeyUtils.generateTokenKey(phone, identity);
			//从Redis中获取token
			String tokenRedis = stringRedisTemplate.opsForValue().get(tokenKey);

			if (StringUtils.isBlank(tokenRedis)) {
				resultString = "token invalid";
				result = false;
			} else {
				//比较从Header的授权中获取token和从Redis中获取的token是否一样
				if (!token.trim().equals(tokenRedis.trim())) {
					resultString = "token invalid";
					result = false;
				}
			}

		}

		//表示捕获到异常
		if (!result) {
			PrintWriter writer = response.getWriter();
			writer.print(JSONObject.fromObject(ResponseResult.fail(resultString)).toString());
		}

		return result;
	}
}
