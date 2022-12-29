package com.gom.mago.error;

import static com.gom.mago.constant.ErrorCode.INTERNAL_ERROR;
import static com.gom.mago.constant.ErrorCode.SPRING_INTERNAL_ERROR;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.gom.mago.dto.ErrorResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class APIExceptionHandler {

    @ExceptionHandler(value = {
            HttpRequestMethodNotSupportedException.class, // httpMethod 잘못된 요청으로 인한 Exception
            MethodArgumentNotValidException.class // @Validation Annotation 에서 걸러진 Exception
    })
    public ResponseEntity<ErrorResponse> handleBadRequest(Exception e, HttpServletRequest request){
    	e.printStackTrace();
    	ErrorResponse errorResponse = ErrorResponse.builder()
                .code(SPRING_INTERNAL_ERROR.getCode())
                .message(SPRING_INTERNAL_ERROR.getMessage())
                .build();
        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e, HttpServletRequest request){
    	e.printStackTrace();
    	ErrorResponse errorResponse = ErrorResponse.builder()
                .code(INTERNAL_ERROR.getCode())
                .message(INTERNAL_ERROR.getMessage())
                .build();
        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
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