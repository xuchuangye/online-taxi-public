package com.mashibing.apipassenger.constraints;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义Validation对于时间判断的注解
 * @author xuchuangye
 * @version 1.0
 * @date 2023/11/27-9:27
 * @description TODO
 */
@Target(value = ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DateTimeRangeParser.class)
public @interface DateTimeRange {

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };

	/**
	 * 在当前时间之后：isAfter
	 * 在当前时间之前：isBefore
	 * @return
	 */
	String judge() default "isAfter";

	String pattern() default "yyyy-MM-dd HH:mm:ss";

	String message() default "日期范围不正确";
}
