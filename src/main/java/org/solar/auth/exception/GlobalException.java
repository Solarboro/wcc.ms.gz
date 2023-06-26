package org.solar.auth.exception;


import lombok.extern.log4j.Log4j2;
import org.solar.auth.controller.res.BaseResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

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

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<BaseResponse> sqlIntegrityConstraintViolationException(DataIntegrityViolationException ex){
        //
        log.warn(ex.getMessage(), ex);
        System.out.println(ex.getMessage());
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setError(ErrorCode.C_00_004.code);
        baseResponse.setMessage(ErrorCode.C_00_001.message + ex.getMessage());
        return ResponseEntity.internalServerError().body(baseResponse);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<BaseResponse> error(BadCredentialsException ex){
        //
        log.warn(ex.getMessage(), ex);
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setError(ErrorCode.UPERROR.code);
        baseResponse.setMessage(ErrorCode.UPERROR.message);
        return ResponseEntity.internalServerError().body(baseResponse);
    }

}
