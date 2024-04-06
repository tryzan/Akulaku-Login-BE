package com.akulaku.service;

import com.akulaku.dto.user.RegisterUserDto;
import com.akulaku.dto.utility.ResponseDto;
import org.springframework.http.ResponseEntity;

public interface UserService {

    ResponseEntity<ResponseDto> registerUser(RegisterUserDto registerUserDto);

    ResponseEntity<ResponseDto> getProfile();
}
