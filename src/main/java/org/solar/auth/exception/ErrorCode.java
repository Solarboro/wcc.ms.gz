package org.solar.auth.exception;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;


@AllArgsConstructor
public enum ErrorCode {
    JWTCONSUMER(200, "JWT parse fail", HttpStatus.BAD_REQUEST),
    JWTPRODUCE(201, "JWT create fail", HttpStatus.BAD_REQUEST),

    PRODUCT_DELFAIL(307,"非新建订单 不允许删除", HttpStatus.BAD_REQUEST),
    PRODUCT_MISMATCH(306,"订单 所有者不匹配", HttpStatus.BAD_REQUEST),
    PRODUCT_NOTFOUND(305,"有效订单不存在 操作不允许", HttpStatus.BAD_REQUEST),
    USERNOTFOUND(304,"User '%s' Not Found", HttpStatus.BAD_REQUEST),
    C_00_003(3,"回退失败 已耗时 %s , 请联系 %s 处理", HttpStatus.BAD_REQUEST),
    C_00_001(1,"发生错误, 请联系管理员", HttpStatus.BAD_REQUEST),
    C_00_002(2,"当前状态 不允许修改", HttpStatus.INTERNAL_SERVER_ERROR);



    int code;
    String message;
    HttpStatus httpStatus;

    public String getMsg(String ...param){
        if(param.length > 0)
            return String.format(message, param);

        return message;
    }

}
