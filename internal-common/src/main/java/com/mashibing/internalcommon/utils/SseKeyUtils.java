package com.mashibing.internalcommon.utils;

/**
 * @author xcy
 * @date 2023/2/23 - 7:43
 */
public class SseKeyUtils {

	/**
	 *
	 * @param userId
	 * @param identity
	 * @return
	 */
	public static String generator(Long userId, String identity) {
		return userId + "$" + identity;
	}
}
