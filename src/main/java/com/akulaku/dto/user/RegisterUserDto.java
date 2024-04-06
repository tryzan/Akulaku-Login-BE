package com.akulaku.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUserDto {

    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "password is required")
    private String password;


    @JsonProperty("first_name")
    @NotBlank(message = "first name is required")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    private String address;

    @JsonProperty("phone_no")
    @NotBlank(message = "phone number is required")
    private String phoneNo;
}
