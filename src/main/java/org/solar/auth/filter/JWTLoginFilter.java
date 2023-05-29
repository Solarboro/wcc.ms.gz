package org.solar.auth.filter;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.solar.auth.controller.dto.LoginStatus;
import org.solar.auth.entity.IUser;
import org.solar.auth.entity.repo.IUserRepo;
import org.solar.auth.service.LoginStatusService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

@Service
@Log4j2
@AllArgsConstructor
public class JWTLoginFilter extends OncePerRequestFilter {
    static final String TOKEN_PREFIX = "Bearer ";

    LoginStatusService loginStatusService;
    IUserRepo iUserRepo;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


        String bearerToken = request.getHeader("Authorization");
        if(StringUtils.hasText(bearerToken) && StringUtils.startsWithIgnoreCase(bearerToken, TOKEN_PREFIX)){
            String token = bearerToken.substring(TOKEN_PREFIX.length());
            LoginStatus check = loginStatusService.check(token);
            if(check != null){
                log.info(check.getUsername() + " found");
                if(System.currentTimeMillis() > check.getExp())
                    log.info("expire " + check.getExp());
                log.info(new Date(check.getExp()));
                IUser byUsername = iUserRepo.findByUsername(check.getUsername());
                SecurityContext context = SecurityContextHolder.getContext();
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(byUsername, byUsername.getPassword(), new ArrayList<>());
                context.setAuthentication(usernamePasswordAuthenticationToken);
            }

        }else{
            log.info("no token");

        }

        filterChain.doFilter(request, response);
    }
}
