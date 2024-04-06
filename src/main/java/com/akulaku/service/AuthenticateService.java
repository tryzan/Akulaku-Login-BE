package com.akulaku.service;

import com.akulaku.dto.authentication.RequestTokenDto;
import com.akulaku.dto.authentication.ResponseTokenDto;
import org.springframework.http.ResponseEntity;

public interface AuthenticateService {
    ResponseEntity<ResponseTokenDto> generateToken(RequestTokenDto requestTokenDto);
}
