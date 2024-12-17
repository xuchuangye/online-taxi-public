package com.mashibing.apipassenger.constraints;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义Validation注解
 *
 * @author xuchuangye
 * @version 1.0
 * @date 2023/11/26-16:40
 * @description TODO
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DicCheckParser.class)
public @interface DicCheck {

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String[] dicValue() default {};

    String message() default "输入的车辆类型不正确";
}
