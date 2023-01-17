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

	AUTH_UNAUTHROIZED(200, "auth.unauthorized", HttpStatus.UNAUTHORIZED),
	AUTH_EMAIL_NOT_VERIFIED(201, "auth.emailNotVerified", HttpStatus.UNAUTHORIZED),
	AUTH_ACCESS_AND_REFRESH_TOKEN_EXPIRE(202, "auth.accessAndRefreshTokenExpire", HttpStatus.UNAUTHORIZED),
	AUTH_REFRESH_TOKEN_NOT_VALID(203, "auth.refreshTokenNotValid", HttpStatus.UNAUTHORIZED),
	AUTH_ACCESS_TOKEN_NOT_VALID(204, "auth.accessTokenNotVliad", HttpStatus.UNAUTHORIZED),
	AUTH_AUTHENTICATE_MEMBER_FAIL(205, "auth.authenticateMemberFail", HttpStatus.NOT_FOUND),
	AUTH_PASSWORD_CONFIRM_NOT_VALID(206, "auth.passwordConfirmNotMatch", HttpStatus.BAD_REQUEST),
	
	MEMBER_DUPLICATE_USER(300, "member.duplicateUser", HttpStatus.BAD_REQUEST),
	MEMBER_USER_NOT_FOUND(201, "member.userNotFound", HttpStatus.NOT_FOUND);

	private final int code;
	private final String message;
	private final HttpStatus httpStatus;

	ErrorCode(int code, String messageCode, HttpStatus httpStatus) {
		this.code = code;
		this.message = MessageUtil.getMessage(messageCode);
		this.httpStatus = httpStatus;
	}

	public String getMessage(Exception e) {
		return getMessage(this.getMessage() + "-" + e.getMessage());
	}

	public String getMessage(String message) {
		return Optional.ofNullable(message).filter(Predicate.not(String::isBlank)).orElse(getMessage());
	}
}