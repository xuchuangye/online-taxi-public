package com.mashibing.internalcommon.response;

import lombok.Data;

/**
 * @author xcy
 * @date 2023/2/10 - 16:00
 */
@Data
public class TokenResponse {
	private String accessToken;
	private String refreshToken;
}
