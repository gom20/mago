package com.gom.mago.error;

import com.gom.mago.constant.ErrorCode;

import lombok.Getter;

@Getter
public class APIException extends RuntimeException{
    private final ErrorCode errorCode;
    private String detailMessage;

    public APIException(ErrorCode errorCode){
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.detailMessage = errorCode.getMessage();
    }

    public APIException(ErrorCode errorCode, String detailMessage){
        super(detailMessage);
        this.errorCode = errorCode;
        this.detailMessage = detailMessage;
    }

}