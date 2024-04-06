package com.akulaku.dto.utility;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDto<T> {
    private boolean success;

    private String message;

    private T Data;
}
