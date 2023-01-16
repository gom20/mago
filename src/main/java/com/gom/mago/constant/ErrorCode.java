package com.gom.mago.constant;

import java.util.Optional;
import java.util.function.Predicate;

import org.springframework.http.HttpStatus;

import com.gom.mago.utils.MessageUtil;

import lombok.Getter;

@Getter
public enum ErrorCode {
    OK(0, "common.success", HttpStatus.OK),
    INTERNAL_SERVER_ERROR(100, "common.internalServerError", HttpStatus.INTERNAL_SERVER_ERROR),
    BAD_REQUEST(101, "common.badRequest", HttpStatus.BAD_REQUEST),

	// auth
    AUTH_EMAIL_NOT_COMPLETED(200, "auth.emailNotAuthenticated", HttpStatus.UNAUTHORIZED),
	AUTHENTICATE_MEMBER_FAIL(207, "auth.checkIdAndPassword", HttpStatus.NOT_FOUND),
    DUPLICATE_USER_EXIST(201, "auth.duplicateUserError", HttpStatus.BAD_REQUEST),
    RESET_PW_FAIL(202, "auth.passwordResetFailError", HttpStatus.BAD_REQUEST),
    
    ACCESS_AND_REFRESH_TOKEN_EXPIRE(203, "auth.refreshTokenExpire", HttpStatus.BAD_REQUEST),
    REFRESH_TOKEN_NOT_VALID(204, "auth.refreshTokenNotValid", HttpStatus.BAD_REQUEST),
	ACCESS_TOKEN_NOT_VALID(205, "auth.refreshTokenNotValid", HttpStatus.UNAUTHORIZED),
	
	PASSWORD_CONFIRM_NOT_MATCH(206, "auth.passwordConfirmNotMatch", HttpStatus.BAD_REQUEST), 
	UNAUTHROIZED(207, "auth.noAuth", HttpStatus.UNAUTHORIZED);
	
	
	private final int code;
    private final String message;
    private final HttpStatus httpStatus;
    
    ErrorCode(int code, String messageCode, HttpStatus httpStatus) {
    	this.code = code;
    	this.message = MessageUtil.getMessage(messageCode);
    	this.httpStatus = httpStatus;
	}

	public String getMessage(Exception e){
        return getMessage(this.getMessage() + "-" + e.getMessage());
    }

    public String getMessage(String message){
        return Optional.ofNullable(message)
                .filter(Predicate.not(String::isBlank))
                .orElse(getMessage());
    }
}