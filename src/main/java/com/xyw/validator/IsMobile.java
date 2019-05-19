package com.xyw.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;

/**
 * Created with IDEA
 * author:RicardoXu
 * Date:2019/5/19
 * Time:16:28
 * 实现自定义注解————检验是否是手机号
 */


@Target({METHOD,FIELD,ANNOTATION_TYPE,CONSTRUCTOR,PARAMETER}) //@Target说明了Annotation所修饰的对象范围
@Retention(RetentionPolicy.RUNTIME)   //@Retention定义了该Annotation被保留的时间长短 RUNTIME:在运行时有效（即运行时保留）
@Documented
@Constraint(validatedBy = {IsMobileValidator.class})
public @interface IsMobile {

    boolean required() default true;
    String message() default "手机号码格式错误";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
