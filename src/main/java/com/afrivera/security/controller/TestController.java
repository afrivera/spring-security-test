package com.afrivera.security.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/v1")
public class TestController {

    @GetMapping("/test")
    public ResponseEntity<String> helloWorld(){
        return ResponseEntity.ok("Hello World!");
    }
    @GetMapping("/test-secured")
    public ResponseEntity<String> helloWorldSecured(){
        return ResponseEntity.ok("Hello World-Secure!");
    }
}
