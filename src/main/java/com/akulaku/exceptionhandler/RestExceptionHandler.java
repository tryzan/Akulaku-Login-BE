package com.akulaku.exceptionhandler;

import com.akulaku.dto.utility.ResponseDto;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<?> handleFailureResponse(CustomException e) {
        ResponseDto responseDto = new ResponseDto();
        responseDto.setSuccess(false);
        responseDto.setMessage(e.getMessage());
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<?> handleFailureResponse(UsernameNotFoundException e) {
        ResponseDto responseDto = new ResponseDto();
        responseDto.setSuccess(false);
        responseDto.setMessage(e.getMessage());
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<?> handleFailureResponse(AuthenticationException e) {
        ResponseDto responseDto = new ResponseDto();
        responseDto.setSuccess(false);
        responseDto.setMessage("Invalid Username or Password");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseDto);
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<?> handleFailureResponse(JwtException e) {
        ResponseDto responseDto = new ResponseDto();
        responseDto.setSuccess(false);
        if(e instanceof MalformedJwtException){
            responseDto.setMessage("Invalid Jwt Token");
        }else if(e instanceof ExpiredJwtException){
            responseDto.setMessage("Token Expired");
        }else {
            responseDto.setMessage(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseDto);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleFailureResponse(MethodArgumentNotValidException e) {
        List<Map<String, Object>> listData = new ArrayList<>();
        Map<String, Object> data = new HashMap<>();
        e.getBindingResult()
                .getAllErrors()
                .forEach(error -> {
                    String fieldName = ((FieldError) error).getField();
                    String errorMessage = error.getDefaultMessage();
                    data.put(fieldName, errorMessage);
                });
        listData.add(data);
        ResponseDto responseDto = new ResponseDto();
        responseDto.setSuccess(false);
        responseDto.setMessage("Payload Validation Error");
        responseDto.setData(listData);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleInternalServerErrorResponse(Exception e) {
        log.error("Exception ", e);
        ResponseDto responseDto = new ResponseDto();
        responseDto.setSuccess(false);
        responseDto.setMessage("Internal Server Error");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDto);
    }
}
