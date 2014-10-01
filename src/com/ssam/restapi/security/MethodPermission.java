package com.ssam.restapi.security;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;

import com.ssam.core.authentication.PermissionType;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface MethodPermission {
	PermissionType value() default PermissionType.NONE;
}
