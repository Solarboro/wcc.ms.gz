package org.solar.auth.controller.dto;

import lombok.Data;

@Data
public class LoginStatus{
    public String username;
    public String token;
    long iss;
    long exp;
}