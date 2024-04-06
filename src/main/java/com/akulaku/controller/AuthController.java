package com.akulaku.controller;

import com.akulaku.dto.authentication.RequestTokenDto;
import com.akulaku.dto.authentication.ResponseTokenDto;
import com.akulaku.service.AuthenticateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthController {

    private AuthenticateService authenticateService;

    public AuthController(AuthenticateService authenticateService){
        this.authenticateService = authenticateService;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<ResponseTokenDto> authentication(@RequestBody RequestTokenDto requestTokenDto){

        return authenticateService.generateToken(requestTokenDto);
    }
}
