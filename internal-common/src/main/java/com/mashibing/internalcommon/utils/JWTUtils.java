package com.mashibing.internalcommon.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT工具类
 *
 * @author xcy
 * @date 2023/2/11 - 16:24
 */
public class JWTUtils {

	/**
	 * 盐
	 */
	private static final String SIGH = "hgaefh^&^A@^231231,,./";
	private static final String JWT_KEY = "passengerPhone";

	/**
	 * 生成token
	 * @param passengerPhone 乘客手机号
	 * @return 返回token
	 */
	public static String generatorToken(String passengerPhone) {

		//设置过期时间
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, 1);
		Date date = calendar.getTime();

		//
		JWTCreator.Builder builder = JWT.create();


		Map<String, String> map = new HashMap<>();
		map.put(JWT_KEY, passengerPhone);
		//整合Map
		map.forEach(
				(k, v) -> {
					builder.withClaim(k, v);
				}
		);
		//整合过期时间
		builder.withExpiresAt(date);
		//整合算法
		return builder.sign(Algorithm.HMAC256(SIGH));
	}


	/**
	 * 解析token
	 * @param token
	 * @return
	 */
	public static String parseToken(String token) {
		DecodedJWT verify = JWT.require(Algorithm.HMAC256(SIGH))
				.build()
				.verify(token);
		Claim claim = verify.getClaim(JWT_KEY);

		return claim.toString();
	}

	public static void main(String[] args) {
		/*Map<String, String> map = new HashMap<>();
		map.put("name", "zhang san");
		map.put("age", "18");
		String sign = generatorToken(map);
		System.out.println("生成的token: " + sign);*/
		String passengerPhone = "18538280980";
		String token = generatorToken(passengerPhone);
		System.out.println("生成token: " + token);
		String s = parseToken(token);
		System.out.println("解析token: " + s);
	}
}
