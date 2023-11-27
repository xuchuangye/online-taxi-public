package com.mashibing.apipassenger.interceptor;

import com.mashibing.internalcommon.constant.CommonStatusEnum;
import com.mashibing.internalcommon.dto.ResponseResult;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局统一异常处理类
 * @author xuchuangye
 * @version 1.0
 * @date 2023/11/27-16:44
 * @description TODO
 */
@RestControllerAdvice
@Order(100)
public class GlobalExceptionHandler {

	@ExceptionHandler(Exception.class)
	public ResponseResult exceptionHandler(Exception e) {
		return ResponseResult.fail(CommonStatusEnum.FAIL.getCode(), CommonStatusEnum.FAIL.getMessage());
	}
}
