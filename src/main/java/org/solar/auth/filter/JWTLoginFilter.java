package org.solar.auth.filter;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.MalformedClaimException;
import org.solar.auth.controller.dto.LoginStatus;
import org.solar.auth.entity.IUser;
import org.solar.auth.entity.repo.IUserRepo;
import org.solar.auth.exception.ErrorCode;
import org.solar.auth.exception.GenericException;
import org.solar.auth.service.JoseForJ;
import org.solar.auth.service.LoginStatusService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
@AllArgsConstructor
public class JWTLoginFilter extends OncePerRequestFilter {
    static final String TOKEN_PREFIX = "Bearer ";

    LoginStatusService loginStatusService;
    IUserRepo iUserRepo;
    JoseForJ joseForJ;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //
        String bearerToken = request.getHeader("Authorization");
        if(StringUtils.hasText(bearerToken) && StringUtils.startsWithIgnoreCase(bearerToken, TOKEN_PREFIX)){

            //
            String token = bearerToken.substring(TOKEN_PREFIX.length());
            try {
                //
                JwtClaims jwtClaims = joseForJ.consume(token);
                String subject = jwtClaims.getSubject();
                List<SimpleGrantedAuthority> simpleGrantedAuthorities = jwtClaims.getStringListClaimValue("g1").stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());

                //
                SecurityContext context = SecurityContextHolder.getContext();
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(subject, null, simpleGrantedAuthorities);
                context.setAuthentication(usernamePasswordAuthenticationToken);

            } catch (Exception e) {
                throw new GenericException(e.getMessage(), e, ErrorCode.JWTCONSUMER);
            }

        }

        filterChain.doFilter(request, response);
    }
}
