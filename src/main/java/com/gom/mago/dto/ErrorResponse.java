package com.gom.mago.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class ErrorResponse {
    private final int code;
    private final String message;
    
    public static ErrorResponse of(int code, String message) {
        return new ErrorResponse(code, message);
    }
}