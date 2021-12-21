package com.swe573.socialhub.controller;

import com.swe573.socialhub.dto.AuthRequest;
import com.swe573.socialhub.dto.AuthResponse;
import com.swe573.socialhub.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/authenticate")
    public AuthResponse createAuthenticationToken(@RequestBody AuthRequest authenticationRequest) throws Exception {
        return userService.createAuthenticationToken(authenticationRequest);
    }
}
