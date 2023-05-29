package org.solar.auth.controller;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.hibernate.annotations.Cache;
import org.solar.auth.entity.IUser;
import org.solar.auth.service.LoginStatusService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;


@CrossOrigin
@RestController
@AllArgsConstructor
@Log4j2
public class LoginController {

    LoginStatusService loginStatusService;
    AuthenticationManager authenticationManager;

    @GetMapping("us/{username}")
    @Cacheable(cacheNames = "iuser", key = "#username")
    public UserDetails getIndex(@PathVariable String username){
        return null;
    }

    @PostMapping("login")
    public LoginResponse login(
            @RequestBody LoginRequest loginRequest
    ){

        //
//        log.info(new BCryptPasswordEncoder().encode(loginRequest.getPassword()));

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(), loginRequest.getPassword()
        );

        Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        System.out.println(authenticate.getClass().getName());
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(loginStatusService.login(loginRequest.getUsername()).getToken());
        return loginResponse;

    }
}


@Data
class LoginRequest{
    String username;
    String password;
}

@Data
class LoginResponse{
    String token;
}