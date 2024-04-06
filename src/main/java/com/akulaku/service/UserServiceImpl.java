package com.akulaku.service;

import com.akulaku.dto.user.ProfileUserDto;
import com.akulaku.dto.user.RegisterUserDto;
import com.akulaku.dto.utility.ResponseDto;
import com.akulaku.entities.User;
import com.akulaku.entities.Role;
import com.akulaku.exceptionhandler.CustomException;
import com.akulaku.repo.UserRepository;
import com.akulaku.repo.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepo;

    private RoleRepository roleRepo;

    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepo, RoleRepository roleRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public ResponseEntity<ResponseDto> registerUser(RegisterUserDto registerUserDto) {
        ResponseDto response = new ResponseDto();
        Set<Role> userRole = new HashSet<>();
        String hashedPassword = passwordEncoder.encode(registerUserDto.getPassword());
        Role role = roleRepo.findById("Customer").orElseThrow(() -> {
            throw new CustomException("Role Not Found");
        });
        userRole.add(role);
        User user = new User(null,registerUserDto.getUsername(),
                hashedPassword,
                registerUserDto.getFirstName(),
                registerUserDto.getLastName(),
                registerUserDto.getAddress(),
                registerUserDto.getPhoneNo(),
                userRole);
        userRepo.save(user);
        response.setSuccess(true);
        response.setMessage("Succesfully Save User");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Override
    public ResponseEntity<ResponseDto> getProfile() {
        ResponseDto responseDto = new ResponseDto();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepo.findByUsername(username).orElseThrow(()->{throw new UsernameNotFoundException("User not found");});
        responseDto.setSuccess(true);
        responseDto.setMessage("Data Found");
        responseDto.setData(new ProfileUserDto(user.getFirstName(),user.getLastName(),user.getAddress(),user.getPhoneNo()));


        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }


}
