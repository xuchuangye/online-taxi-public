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
	 * Validation对于属性的验证虽然已经进行了封装，但是用户看不到，并且对于用户来说提示信息不友好
	 * @param e
	 * @return
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseResult validationExceptionHandler(MethodArgumentNotValidException e) {
		return ResponseResult.fail(CommonStatusEnum.VALIDATION_EXCEPTION.getCode(), e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
	}

	/**
	 * 将Validation对于参数的验证出现的异常进行封装，并且针对message属性进行修改，给用户友好的提示信息
	 * @param e
	 * @return
	 */
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseResult ConstraintValidationExceptionHandler(ConstraintViolationException e) {
		//用户友好提示信息
		String message = "";
		Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
		for (ConstraintViolation<?> constraintViolation : constraintViolations) {
			message = constraintViolation.getMessage();
		}
		return ResponseResult.fail(CommonStatusEnum.VALIDATION_EXCEPTION.getCode(), message);
	}
}
