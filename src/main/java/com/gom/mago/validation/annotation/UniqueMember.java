package com.gom.mago.validation.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;

import com.gom.mago.validation.UniqueMemberValidator;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueMemberValidator.class)
public @interface UniqueMember {
	  String message() default "이미 가입된 이메일 입니다.";
	  Class[] groups() default {};
	  Class[] payload() default {};
}
