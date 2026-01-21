package org.solar.auth.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.hibernate.annotations.Cache;
import org.solar.auth.entity.IUser;
import org.solar.auth.entity.repo.IUserRepo;
import org.solar.auth.exception.ErrorCode;
import org.solar.auth.exception.GenericException;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
@Log4j2
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    IUserRepo iUserRepo;

    @Override
    public UserDetails loadUserByUsername(String username) {

        //
        IUser iUser = iUserRepo.findByUsername(username);

        //
        if(iUser == null)
            throw new GenericException(ErrorCode.USERNOTFOUND, username);

        //
        return iUser.getUser();
    }


}
