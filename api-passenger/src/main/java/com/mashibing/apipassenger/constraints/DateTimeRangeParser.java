package com.mashibing.apipassenger.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

/**
 * 自定义Validation对于时间判断的注解的解析器
 *
 * @author xuchuangye
 * @version 1.0
 * @date 2023/11/26-16:43
 * @description TODO
 */
public class DateTimeRangeParser implements ConstraintValidator<DateTimeRange, Object> {

	private DateTimeRange dateTimeRange;

	@Override
	public void initialize(DateTimeRange constraintAnnotation) {
		ConstraintValidator.super.initialize(constraintAnnotation);
	}

	/**
	 * 校验用户传入的时间是否在当前时间之后
	 *
	 * @param paramDate 用户传入的日期参数
	 * @param context   上下文
	 * @return 如果是的话，返回true，如果不是，返回false
	 */
	@Override
	public boolean isValid(Object paramDate, ConstraintValidatorContext context) {
		LocalDateTime localDateTime = null;

		//如果用户传入的日期参数为空，不需要进行判断，直接返回true
		if (paramDate == null) {
			return true;
		}

		if (paramDate instanceof LocalDateTime) {
			localDateTime = (LocalDateTime) paramDate;
		}

		if (paramDate instanceof String) {
			localDateTime = LocalDateTime.parse(paramDate.toString(), DateTimeFormatter.ofPattern(dateTimeRange.pattern()));
		}

		LocalDateTime now = LocalDateTime.now();


		return localDateTime != null && localDateTime.isAfter(now);
	}

}

