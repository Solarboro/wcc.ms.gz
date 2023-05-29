package org.solar.auth.exception;

import org.solar.auth.controller.res.BaseResponse;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public class GenericException extends RuntimeException{

    ErrorCode errorCode;

    public GenericException(ErrorCode errorCode) {
        super(errorCode.message);
        this.errorCode = errorCode;
    }

    public GenericException(String message, ErrorCode errorCode) {
        super(Optional.ofNullable(message).orElse(errorCode.message));
        this.errorCode = errorCode;
    }

    public GenericException(String message, Throwable cause, ErrorCode errorCode) {
        super(Optional.ofNullable(message).orElse(errorCode.message), cause);
        this.errorCode = errorCode;
    }


    public ResponseEntity<BaseResponse> buildResponse(){
        //
        BaseResponse baseResponse = new BaseResponse(errorCode.code, errorCode.message);

        //
        return new ResponseEntity<>(baseResponse, errorCode.httpStatus);
    }

}
