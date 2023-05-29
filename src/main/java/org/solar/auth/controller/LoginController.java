package org.solar.auth.controller;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.hibernate.annotations.Cache;
import org.solar.auth.entity.IUser;
import org.solar.auth.service.JoseForJ;
import org.solar.auth.service.LoginStatusService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;


@CrossOrigin
@RestController
@AllArgsConstructor
@Log4j2
public class LoginController {

    LoginStatusService loginStatusService;
    AuthenticationManager authenticationManager;
    JoseForJ joseForJ;


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

        // Authen flow
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(), loginRequest.getPassword()
        );
        Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        User user = (User) authenticate.getPrincipal();

        // gen JWT
        String jwt = joseForJ.produce(user.getUsername(), user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));

        // return
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwt);
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