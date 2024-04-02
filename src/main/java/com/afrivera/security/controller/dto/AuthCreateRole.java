package com.afrivera.security.controller.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthCreateRole {

    @Size(max = 3, message = "The user can't have more than 3 roles")
    private List<String> roles;
}
