package com.portfolio.bleustudio.auth.annotation;

import com.portfolio.bleustudio.auth.enums.AccessLevelEnum;
import com.portfolio.bleustudio.auth.enums.AuthLevel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AccessLevel {

    AccessLevelEnum authentication() default AccessLevelEnum.PRIVATE;

    AuthLevel authority() default AuthLevel.MANAGER;
}
