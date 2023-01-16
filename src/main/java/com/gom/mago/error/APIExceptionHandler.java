package com.gom.mago.error;

import static com.gom.mago.constant.ErrorCode.BAD_REQUEST;
import static com.gom.mago.constant.ErrorCode.INTERNAL_SERVER_ERROR;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.gom.mago.dto.ErrorResponse;

@RestControllerAdvice
public class APIExceptionHandler {

   @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e, HttpServletRequest request){
    	e.printStackTrace();
    	ErrorResponse errorResponse = ErrorResponse.builder()
                .code(INTERNAL_SERVER_ERROR.getCode())
                .message(INTERNAL_SERVER_ERROR.getMessage())
                .build();
        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {
            HttpRequestMethodNotSupportedException.class // httpMethod 잘못된 요청으로 인한 Exception
    })
    public ResponseEntity<ErrorResponse> handleBadMethodRequest(HttpRequestMethodNotSupportedException e, HttpServletRequest request){
    	e.printStackTrace();
    	ErrorResponse errorResponse = ErrorResponse.builder()
                .code(BAD_REQUEST.getCode())
                .message(BAD_REQUEST.getMessage())
                .build();
        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(value = {
            MethodArgumentNotValidException.class // @Validation Annotation 에서 걸러진 Exception
    })
    public ResponseEntity<ErrorResponse> handleBadArgumentRequest(MethodArgumentNotValidException e, HttpServletRequest request){
    	e.printStackTrace();
    	ErrorResponse errorResponse = ErrorResponse.builder()
                .code(BAD_REQUEST.getCode())
                .message(e.getFieldError().getDefaultMessage())
                .build();
        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST);
    }

 
    @ExceptionHandler(APIException.class)
    public ResponseEntity<ErrorResponse> handleException(APIException e, HttpServletRequest request){
    	ErrorResponse errorResponse = ErrorResponse.builder()
                .code(e.getErrorCode().getCode())
                .message(e.getErrorCode().getMessage())
                .build();
        return new ResponseEntity<ErrorResponse>(errorResponse, e.getErrorCode().getHttpStatus());
    }

}