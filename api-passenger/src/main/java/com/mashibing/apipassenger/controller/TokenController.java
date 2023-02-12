package com.mashibing.apipassenger.controller;

import com.mashibing.apipassenger.service.TokenService;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.TokenDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xcy
 * @date 2023/2/12 - 16:50
 */
@RestController
public class TokenController {

	@Autowired
	private TokenService tokenService;

	@PostMapping("/token-refresh")
	public ResponseResult refreshToken(@RequestBody TokenDTO tokenDTO) {
		String refreshTokenSrc = tokenDTO.getRefreshToken();
		System.out.println("原来的refreshToken: " + refreshTokenSrc);

		return tokenService.refreshToken(refreshTokenSrc);
	}
}
