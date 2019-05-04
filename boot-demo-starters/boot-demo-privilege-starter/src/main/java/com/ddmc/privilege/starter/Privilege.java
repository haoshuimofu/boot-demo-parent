package com.ddmc.privilege.starter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Privilege {
    // 认证字符串
    String authTtem() default "";

    // 认证字符串别名
    String alias();

}
