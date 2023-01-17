package com.gom.mago.dto;

import com.gom.mago.constant.ErrorCode;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
public class APIResponse<T> extends ErrorResponse {
	private final T data;

	private APIResponse(T data) {
		super(ErrorCode.OK.getCode(), ErrorCode.OK.getMessage());
		this.data = data;
	}

	public APIResponse() {
		super(ErrorCode.OK.getCode(), ErrorCode.OK.getMessage());
		this.data = null;
	}

	public static <T> APIResponse<T> of(T data) {
		return new APIResponse<T>(data);
	}
}