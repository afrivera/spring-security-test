package com.afrivera.security.controller.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUser {

    @NotBlank
    private String username;
    @NotBlank
    private String password;

    @Valid
    private AuthCreateRole roles;
}
