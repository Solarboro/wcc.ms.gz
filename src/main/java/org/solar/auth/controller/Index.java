package org.solar.auth.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.solar.auth.controller.dto.CustomerDTO;
import org.solar.auth.controller.dto.LoginStatus;
import org.solar.auth.controller.res.BaseResponse;
import org.solar.auth.entity.Authorities;
import org.solar.auth.entity.IUser;
import org.solar.auth.entity.repo.IUserRepo;
import org.solar.auth.entity.wcc.Product;
import org.solar.auth.exception.ErrorCode;
import org.solar.auth.exception.GenericException;
import org.solar.auth.service.wcc.impl.ProductService;
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

    ProductService productService;


    @GetMapping("user")
    public IUser get( Authentication authentication) throws JsonProcessingException {

        return iUserRepo.findById((Long)authentication.getPrincipal()).get();
    }

    @PostMapping("user")
    public Product upload(@RequestBody Product iUser, Principal principal) throws JsonProcessingException {

        return productService.newProduct(iUser, principal.getName());
    }

    @RequestMapping("index2/{value}")
    public IUser index(@PathVariable String value, Principal principal, Authentication authentication){


        System.out.println(principal.getClass().getName());
        System.out.println(authentication.getClass().getName());

        System.out.println(authentication.getPrincipal());
        System.out.println(authentication.getPrincipal().getClass().getName());

        System.out.println(authentication.getCredentials());
        System.out.println(authentication.getCredentials().getClass().getName());

        return null;
    }


}
