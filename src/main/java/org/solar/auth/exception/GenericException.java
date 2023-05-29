package org.solar.auth.exception;

import org.solar.auth.controller.res.BaseResponse;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public class GenericException extends RuntimeException{

    ErrorCode errorCode;

    public GenericException(ErrorCode errorCode, String ...param) {
        super(errorCode.getMsg(param));
        this.errorCode = errorCode;
    }

    public GenericException(String message, ErrorCode errorCode, String ...param) {
        super(Optional.ofNullable(message).orElse(errorCode.getMsg(param)));
        this.errorCode = errorCode;
    }

    public GenericException(String message, Throwable cause, ErrorCode errorCode, String ...param) {
        super(Optional.ofNullable(message).orElse(errorCode.getMsg(param)), cause);
        this.errorCode = errorCode;
    }

    public ResponseEntity<BaseResponse> buildResponse(){
        //
        BaseResponse baseResponse = new BaseResponse(errorCode.code, errorCode.message);

        //
        return new ResponseEntity<>(baseResponse, errorCode.httpStatus);
    }

}
