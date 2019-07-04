package com.github.yeecode.objectLogger.client.annotation;

import com.github.yeecode.objectLogger.client.handler.BuiltinTypeHandler;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LogTag {
    String alias() default "";

    BuiltinTypeHandler builtinType() default BuiltinTypeHandler.NORMAL;

    String extendedType() default "";
}
