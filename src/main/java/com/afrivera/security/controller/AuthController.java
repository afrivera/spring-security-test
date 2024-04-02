package com.afrivera.security.controller;

import com.afrivera.security.controller.dto.AuthLogin;
import com.afrivera.security.controller.dto.AuthResponse;
import com.afrivera.security.controller.dto.RegisterUser;
import com.afrivera.security.service.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth/v1")
@RequiredArgsConstructor
public class AuthController {

    private final IUserService userService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid AuthLogin authLogin){
        return new ResponseEntity<AuthResponse>(userService.login(authLogin), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody @Valid RegisterUser registerUser){
        return new ResponseEntity<AuthResponse>(userService.register(registerUser), HttpStatus.CREATED);
    }
}
