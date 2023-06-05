package org.solar.auth.controller;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.solar.auth.entity.Authorities;
import org.solar.auth.entity.IUser;
import org.solar.auth.entity.repo.IUserRepo;
import org.solar.auth.service.JoseForJ;
import org.springframework.beans.BeanUtils;
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
import java.util.Optional;
import java.util.stream.Collectors;


@CrossOrigin
@RestController
@AllArgsConstructor
@Log4j2
public class LoginController {

    AuthenticationManager authenticationManager;
    IUserRepo iUserRepo;
    JoseForJ joseForJ;

    @PostMapping("user")
    public IUser newUser(@RequestBody IUser iUser){

        IUser raw = new IUser();
        BeanUtils.copyProperties(iUser, raw);

        //
        iUser.setId(null);
        iUser.setAuthoritiesList(new ArrayList<>());
        iUser.setRawPassword(iUser.getPassword());
        iUser.setPassword(new BCryptPasswordEncoder().encode(iUser.getRawPassword()));

        return iUserRepo.saveAndFlush(raw);
    }

    @PutMapping("user")
    public IUser putUser(@RequestBody IUser iUser, Authentication authentication){
        Long uid = (Long) authentication.getPrincipal();
        IUser raw = iUserRepo.findById(uid).get();

        Optional.ofNullable(iUser.getLastname()).ifPresent(value -> raw.setLastname(value));
        Optional.ofNullable(iUser.getFirstname()).ifPresent(value -> raw.setFirstname(value));
        Optional.ofNullable(iUser.getPassword()).ifPresent(value -> {
            raw.setPassword(new BCryptPasswordEncoder().encode(value));
            raw.setRawPassword(value);
        });

        iUserRepo.flush();
        return raw;
    }


    @PostMapping("login")
    public LoginResponse login(
            @RequestBody LoginRequest loginRequest
    ){

        //
//        log.info(new BCryptPasswordEncoder().encode(loginRequest.getPassword()));
        //$2a$10$6vWec8gN0UQVOf0CjBZUreLNkVdQoP3aBax5lH4k3iFaOg45lnJBi

        // Authen flow
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(), loginRequest.getPassword()
        );
        Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        //
        IUser user = iUserRepo.findByUsername(loginRequest.getUsername());

        // gen JWT
        String jwt = joseForJ.produce(user.getId(), user.getAuthoritiesList().stream().map(Authorities::getRole).collect(Collectors.toList()));

        // return
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwt);
        loginResponse.setIUser(user);
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
    IUser iUser;
}