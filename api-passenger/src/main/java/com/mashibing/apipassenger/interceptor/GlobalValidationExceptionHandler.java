package com.mashibing.apipassenger.interceptor;

import com.mashibing.internalcommon.constant.CommonStatusEnum;
import com.mashibing.internalcommon.dto.ResponseResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

/**
 * 全局Validation验证框架的异常处理类
 * @author xuchuangye
 * @version 1.0
 * @date 2023/11/26-10:29
 * @description TODO
 */
@RestControllerAdvice
public class GlobalValidationExceptionHandler {
	/**
	 * Validation对于属性的验证虽然进行了封装，但是用户不可见，并且提示信息对用户来说并不友好，所以对响应值做统一处理
	 * @param e
	 * @return
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseResult validationExceptionHandler(MethodArgumentNotValidException e) {
		return ResponseResult.fail(CommonStatusEnum.VALIDATION_EXCEPTION.getCode(), e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
	}

	/**
	 * Validation对于参数的验证出现的异常进行捕获并且对响应值做统一处理，并且对message属性进行修改，给用户友好的提示信息
	 * @param e
	 * @return
	 */
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseResult constraintValidationExceptionHandler(ConstraintViolationException e) {
		String message = "";
		Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
		for (ConstraintViolation<?> constraintViolation : constraintViolations) {
			message = constraintViolation.getMessage();
		}
		return ResponseResult.fail(CommonStatusEnum.VALIDATION_EXCEPTION.getCode(), message);
	}
}
