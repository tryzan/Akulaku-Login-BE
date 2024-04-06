package com.akulaku.controller;

import com.akulaku.dto.user.RegisterUserDto;
import com.akulaku.dto.utility.ResponseDto;
import com.akulaku.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseDto> register(@Valid @RequestBody RegisterUserDto registerUserDto){
        return userService.registerUser(registerUserDto);
    }
    @GetMapping("/profile")
    public ResponseEntity<ResponseDto> getProfile(){
        return userService.getProfile();
    }

}
