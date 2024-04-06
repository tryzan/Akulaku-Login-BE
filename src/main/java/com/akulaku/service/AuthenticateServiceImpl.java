package com.akulaku.service;

import com.akulaku.config.JwtTokenService;
import com.akulaku.dto.authentication.RequestTokenDto;
import com.akulaku.dto.authentication.ResponseTokenDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AuthenticateServiceImpl implements AuthenticateService{

    private AuthenticationManager authenticationManager;

    private JwtTokenService jwtTokenService;

    @Value("${expirationToken}")
    private Long ExpToken;


    public AuthenticateServiceImpl(AuthenticationManager authenticationManager,JwtTokenService jwtTokenService){
        this.jwtTokenService = jwtTokenService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public ResponseEntity<ResponseTokenDto> generateToken(RequestTokenDto request) {

        ResponseTokenDto response = new ResponseTokenDto();
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
            Date expireAt = new Date(System.currentTimeMillis()+ExpToken);
            String token = jwtTokenService.generateToken(request.getUsername(),new Date(System.currentTimeMillis()),expireAt);
            response.setSuccess(true);
            response.setToken(token);
            response.setMessage("Succesfully Generated Token");
            response.setExpire(expireAt);
            return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
