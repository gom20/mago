package com.gom.mgo.constant;

import java.util.Optional;
import java.util.function.Predicate;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    OK(0, "Success"),
    BAD_REQUEST(10000, "Bad Request"),
    INTERNAL_ERROR(20000, "Server Internal Error"),
    SPRING_INTERNAL_ERROR(20001, "Spring Internal Error");

    private final Integer code;
    private final String message;

    public String getMessage(Exception e){
        return getMessage(this.getMessage() + "-" + e.getMessage());
    }

    public String getMessage(String message){
        return Optional.ofNullable(message)
                .filter(Predicate.not(String::isBlank))
                .orElse(getMessage());
    }
}