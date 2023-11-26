package com.mashibing.apipassenger.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

/**
 * 自定义Validation注解的解析器
 * @author xuchuangye
 * @version 1.0
 * @date 2023/11/26-16:43
 * @description TODO
 */
public class VehicleTypeParser implements ConstraintValidator<VehicleTypeCheck, String> {

	private List<String> vehicleTypeArray = null;

	@Override
	public void initialize(VehicleTypeCheck constraintAnnotation) {
		vehicleTypeArray = Arrays.asList(constraintAnnotation.vehicleTypeValue());
		ConstraintValidator.super.initialize(constraintAnnotation);
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (vehicleTypeArray.contains(value)) {
			return true;
		}
		return false;
	}
}
