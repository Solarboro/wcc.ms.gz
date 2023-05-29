package org.solar.auth.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.solar.auth.controller.dto.CustomerDTO;
import org.solar.auth.controller.dto.LoginStatus;
import org.solar.auth.controller.res.BaseResponse;
import org.solar.auth.entity.IUser;
import org.solar.auth.entity.repo.IUserRepo;
import org.solar.auth.exception.ErrorCode;
import org.solar.auth.exception.GenericException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@RestController
@AllArgsConstructor
@Log4j2
public class Index {

    IUserRepo iUserRepo;


    @PostMapping("upload")
    public CustomerDTO upload(@RequestBody CustomerDTO iUser) throws JsonProcessingException {

        //
        if(true)
            throw new GenericException(ErrorCode.C_00_002);
        System.out.println(new ObjectMapper().writeValueAsString(iUser));
        return iUser;
    }

    @RequestMapping("index2/{value}")
    public IUser index(@PathVariable String value, Principal principal, Authentication authentication){



        log.info(principal.getClass().getName());
        log.info(authentication.getClass().getName());
        log.info(authentication.getPrincipal().getClass().getName());
        log.info(authentication.getName());

        log.info(principal.getName());
        IUser principal1 = (IUser) authentication.getPrincipal();
        log.info(principal1.getUsername());

        Optional<IUser> byId = iUserRepo.findById(2L);
        byId.get().setPassword(value);
        IUser iUser = byId.get();

        iUserRepo.flush();
        return iUser;
    }


}
