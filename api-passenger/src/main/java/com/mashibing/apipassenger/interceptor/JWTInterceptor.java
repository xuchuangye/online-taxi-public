package com.mashibing.apipassenger.interceptor;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.utils.JWTUtils;
import net.sf.json.JSONObject;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @author xcy
 * @date 2023/2/12 - 9:56
 */
public class JWTInterceptor implements HandlerInterceptor {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		//是否拦截，True表示是，false表示否
		boolean result = true;
		//处理并捕获异常的提示信息
		String resultString = "";
		//从Header的授权中获取token
		String token = request.getHeader("Authorization");

		try {
			JWTUtils.parseToken(token);
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
		//表示捕获到异常
		if (!result) {
			PrintWriter writer = response.getWriter();
			writer.print(JSONObject.fromObject(ResponseResult.fail(resultString)).toString());
		}

		return result;
	}
}
