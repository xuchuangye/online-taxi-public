package com.mashibing.internalcommon.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.mashibing.internalcommon.dto.TokenResult;

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
	/**
	 * 手机号
	 */
	private static final String JWT_KEY_PHONE = "phone";
	/**
	 * 身份标识
	 */
	private static final String JWT_KEY_IDENTITY = "identity";

	/**
	 * 生成token
	 *
	 * @param phone 手机号
	 * @param identity 身份标识
	 * @return 返回token
	 */
	public static String generatorToken(String phone, String identity) {

		//设置过期时间
		/*Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, 1);
		Date date = calendar.getTime();*/

		//
		JWTCreator.Builder builder = JWT.create();


		Map<String, String> map = new HashMap<>();
		map.put(JWT_KEY_PHONE, phone);
		map.put(JWT_KEY_IDENTITY, identity);
		//整合Map
		map.forEach(
				/*(k, v) -> {
					builder.withClaim(k, v);
				}*/
				builder::withClaim
		);
		//整合过期时间
		//builder.withExpiresAt(date);
		//整合算法
		return builder.sign(Algorithm.HMAC256(SIGH));
	}


	/**
	 * 解析token
	 *
	 * @param token
	 * @return
	 */
	public static TokenResult parseToken(String token) {
		DecodedJWT verify = JWT.require(Algorithm.HMAC256(SIGH)).build().verify(token);
		//如果本身就是字符串，使用toString()时，会有两对""
		//因此必须使用asString()，字符串仍然作为它本身
		String phone = verify.getClaim(JWT_KEY_PHONE).asString();
		String identity = verify.getClaim(JWT_KEY_IDENTITY).asString();

		TokenResult tokenResult = new TokenResult();
		tokenResult.setPhone(phone);
		tokenResult.setIdentity(identity);

		return tokenResult;
	}

	public static void main(String[] args) {
		/*Map<String, String> map = new HashMap<>();
		map.put("name", "zhang san");
		map.put("age", "18");
		String sign = generatorToken(map);
		System.out.println("生成的token: " + sign);*/
		/*String passengerPhone = "18538280980";
		String token = generatorToken(passengerPhone);
		System.out.println("生成token: " + token);
		String s = parseToken(token);
		System.out.println("解析token: " + s);*/

		String token = generatorToken("18528380690", "1");
		System.out.println("token：" + token);
		TokenResult tokenResult = parseToken(token);
		System.out.println("手机号：" + tokenResult.getPhone());
		System.out.println("身份标识：" + tokenResult.getIdentity());
	}
}
