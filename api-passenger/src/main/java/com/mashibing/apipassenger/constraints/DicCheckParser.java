package com.mashibing.apipassenger.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

/**
 * 自定义Validation注解的解析器
 *
 * @author xuchuangye
 * @version 1.0
 * @date 2023/11/26-16:43
 * @description TODO
 */
public class DicCheckParser implements ConstraintValidator<DicCheck, Object> {

	private List<String> dicValue = null;

	@Override
	public void initialize(DicCheck constraintAnnotation) {
		dicValue = Arrays.asList(constraintAnnotation.dicValue());
		ConstraintValidator.super.initialize(constraintAnnotation);
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		System.out.println("自定义Validation校验注解开始进行校验...");
		if (value instanceof Integer) {
			return dicValue.contains(String.valueOf(value));
		}
		if (value instanceof String) {
			return dicValue.contains(value);
		}
		return false;
	}
}
