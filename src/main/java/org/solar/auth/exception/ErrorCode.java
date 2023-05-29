package org.solar.auth.exception;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;


@AllArgsConstructor
public enum ErrorCode {

    C_00_001(1,"COMMON Error 1", HttpStatus.BAD_REQUEST),
    C_00_002(2,"COMMON Error 2", HttpStatus.INTERNAL_SERVER_ERROR);

    int code;
    String message;
    HttpStatus httpStatus;

}
