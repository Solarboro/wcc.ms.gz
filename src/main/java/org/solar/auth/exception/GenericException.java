package org.solar.auth.exception;

import org.solar.auth.controller.res.BaseResponse;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public class GenericException extends RuntimeException{

    ErrorCode errorCode;
    BaseResponse baseResponse;

    public GenericException(ErrorCode errorCode, String ...param) {
        super(errorCode.getMsg(param));
        this.errorCode = errorCode;
        baseResponse = new BaseResponse(errorCode.code, errorCode.getMsg(param));
    }

    public GenericException(String message, ErrorCode errorCode, String ...param) {
        super(Optional.ofNullable(message).orElse(errorCode.getMsg(param)));
        this.errorCode = errorCode;
        baseResponse = new BaseResponse(errorCode.code, errorCode.getMsg(param));

    }

    public GenericException(String message, Throwable cause, ErrorCode errorCode, String ...param) {
        super(Optional.ofNullable(message).orElse(errorCode.getMsg(param)), cause);
        this.errorCode = errorCode;
        baseResponse = new BaseResponse(errorCode.code, errorCode.getMsg(param));

    }

    public ResponseEntity<BaseResponse> buildResponse(){
                //
        return new ResponseEntity<>(baseResponse, errorCode.httpStatus);
    }

}
