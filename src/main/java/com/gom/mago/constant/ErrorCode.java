package com.gom.mago.constant;

import java.util.Optional;
import java.util.function.Predicate;

import org.springframework.http.HttpStatus;

import com.gom.mago.utils.MessageUtil;

import lombok.Getter;

@Getter
public enum ErrorCode {
    OK(0, "common.success", HttpStatus.OK),
    BAD_REQUEST(101, "common.badRequest", HttpStatus.BAD_REQUEST),
    INTERNAL_ERROR(102, "common.internalServerError", HttpStatus.INTERNAL_SERVER_ERROR),
    SPRING_INTERNAL_ERROR(103, "common.internalServerError", HttpStatus.INTERNAL_SERVER_ERROR),

	// auth
	LOGIN_FAIL_ERROR(200, "auth.checkIdAndPassword", HttpStatus.NOT_FOUND),
    DUPLICATE_USER_ERROR(201, "auth.duplicateUserError", HttpStatus.BAD_REQUEST),
    PW_RESET_FAIL_ERROR(202, "auth.passwordResetFailError", HttpStatus.BAD_REQUEST);
	
	
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