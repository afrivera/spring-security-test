package com.afrivera.security.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/access/v1")
@PreAuthorize("denyAll()")
public class TestController {

    @GetMapping("/test")
    @PreAuthorize("permitAll()")
    public ResponseEntity<String> helloWorld(){
        return ResponseEntity.ok("Hello World!");
    }
    @GetMapping("/test-secured")
    @PreAuthorize("hasAuthority('READ')")
    public ResponseEntity<String> helloWorldSecured(){
        return ResponseEntity.ok("Hello World-Secure!");
    }
}
