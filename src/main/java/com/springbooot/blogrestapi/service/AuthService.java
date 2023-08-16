package com.springbooot.blogrestapi.service;

import com.springbooot.blogrestapi.dto.LoginDto;
import com.springbooot.blogrestapi.dto.RegisterDto;

public interface AuthService {
    String registerUser(RegisterDto registerDto);
    String LoginUser(LoginDto loginDto);
}
