package org.solar.auth.exception;


import lombok.extern.log4j.Log4j2;
import org.solar.auth.controller.res.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Log4j2
public class GlobalException {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse> error(Exception ex){
        //
        log.warn(ex.getMessage(), ex);
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setError(ErrorCode.C_00_001.code);
        baseResponse.setMessage(ErrorCode.C_00_001.message);
        return ResponseEntity.internalServerError().body(baseResponse);
    }

    @ExceptionHandler(GenericException.class)
    public ResponseEntity<BaseResponse> generic(GenericException ex){

        //
        log.warn(ex.getMessage(), ex);

        //
        return ex.buildResponse();
    }
}
