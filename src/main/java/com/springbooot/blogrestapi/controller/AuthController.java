package com.springbooot.blogrestapi.controller;

import com.springbooot.blogrestapi.dto.JWTAuthResponse;
import com.springbooot.blogrestapi.dto.LoginDto;
import com.springbooot.blogrestapi.dto.RegisterDto;
import com.springbooot.blogrestapi.service.AuthService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping(value = {"/register", "/signup"})
    public ResponseEntity<String> registerUser(@Valid @RequestBody RegisterDto registerDto) {
        return new ResponseEntity<>(authService.registerUser(registerDto), HttpStatus.CREATED);
    }


    @PostMapping(value = {"/login", "/signin"})
    public ResponseEntity<JWTAuthResponse> loginUser(@Valid @RequestBody LoginDto loginDto) {
        String token = authService.LoginUser(loginDto);

        JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();
        jwtAuthResponse.setAccessToken(token);
        return ResponseEntity.ok(jwtAuthResponse);
    }

}
