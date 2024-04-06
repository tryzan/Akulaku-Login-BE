package com.akulaku.config;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

import java.io.IOException;

@Slf4j
@Configuration
public class JwtRequestFilter extends OncePerRequestFilter {

    private UserDetailsService userDetailsService;

    private JwtTokenService jwtTokenService;

    private HandlerExceptionResolver handlerExceptionResolver;

    public JwtRequestFilter(UserDetailsService userDetailsService,JwtTokenService jwtTokenService, HandlerExceptionResolver handlerExceptionResolver){
        this.userDetailsService = userDetailsService;
        this.jwtTokenService = jwtTokenService;
        this.handlerExceptionResolver = handlerExceptionResolver;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");
        String username = null;
        String token = null;

        if (authorizationHeader != null) {

            token = authorizationHeader.replace("Bearer", "");
            try {
                username = jwtTokenService.getUsername(token);
            }catch(JwtException e){
                handlerExceptionResolver.resolveException(request,response,null,e);
                return;
            }
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (username != null && authentication == null) {

            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (jwtTokenService.validateToken(token, userDetails)) {

                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}
