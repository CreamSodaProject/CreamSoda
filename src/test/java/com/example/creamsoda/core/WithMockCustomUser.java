package com.example.creamsoda.core;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockCustomAccountSecurityContextFactory.class)
public @interface WithMockCustomUser {

	int id() default 1;
	String name() default "David";
	String email() default "David@naver.com";
}