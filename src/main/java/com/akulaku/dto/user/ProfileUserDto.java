package com.akulaku.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileUserDto {
    private String firstName;

    private String lastName;

    private String address;

    private String phoneNo;
}
