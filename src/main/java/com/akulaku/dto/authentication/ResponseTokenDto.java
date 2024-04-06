package com.akulaku.dto.authentication;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseTokenDto {

    private boolean success;

    private String message;

    private String token;

    private Date expire;

}
