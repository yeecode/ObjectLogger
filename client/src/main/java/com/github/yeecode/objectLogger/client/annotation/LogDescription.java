package com.github.yeecode.objectLogger.client.annotation;

import com.github.yeecode.objectLogger.client.constant.AttributeTypeEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LogDescription {
    String name() default "";
    AttributeTypeEnum type() default AttributeTypeEnum.NORMAL;
    String localType() default "";
}
