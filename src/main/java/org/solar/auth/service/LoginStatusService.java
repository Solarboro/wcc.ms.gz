package org.solar.auth.service;

import lombok.Data;
import org.hibernate.annotations.Cache;
import org.solar.auth.controller.dto.LoginStatus;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class LoginStatusService {

    @CachePut(cacheNames = "login", key = "#result.token")
    public LoginStatus login(String username){
        long issueAt = System.currentTimeMillis();
        LoginStatus loginStatus = new LoginStatus();
        loginStatus.setToken(UUID.randomUUID().toString());
        loginStatus.setUsername(username);
        loginStatus.setIss(issueAt);
        loginStatus.setExp(issueAt + 1 * 60 * 60 * 1000);
        return loginStatus;
    }

    @Cacheable(cacheNames = "login", key = "#token")
    public LoginStatus check(String token){
        return null;
    }

    @CacheEvict(cacheNames = "login", key = "#token")
    public void logout(String token){
    }
}

