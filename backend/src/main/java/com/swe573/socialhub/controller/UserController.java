package com.swe573.socialhub.controller;

import com.swe573.socialhub.dto.AuthRequest;
import com.swe573.socialhub.dto.AuthResponse;
import com.swe573.socialhub.dto.UserDto;
import com.swe573.socialhub.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.security.sasl.AuthenticationException;
import java.security.Principal;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService service;


    @CrossOrigin(origins = "http://localhost:8081")

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> signUp(@RequestBody UserDto params) {
        try {
            service.register(params);
            return ResponseEntity.ok(service.createAuthenticationToken(new AuthRequest(params.getUsername(), params.getPassword())));
        } catch (IllegalArgumentException | DuplicateKeyException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getLocalizedMessage());
        } catch (AuthenticationException e) {
            throw new IllegalStateException();
        }
    }

    @CrossOrigin(origins = "http://localhost:8081")
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody UserDto params) {
        try {
            service.login(params);
            return ResponseEntity.ok(service.createAuthenticationToken(new AuthRequest(params.getUsername(), params.getPassword())));
        } catch (IllegalArgumentException | DuplicateKeyException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getLocalizedMessage());
        } catch (AuthenticationException e) {
            throw new IllegalStateException();
        }
    }

    @GetMapping("/user/{userId}")
    public UserDto getUser(Principal principal, @PathVariable String userId) {
        try {
            if (userId.length() == 36) {
                return service.getUser(userId, principal);
            } else {
                return service.getUserByUsername(userId, principal);
            }
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getLocalizedMessage());
        }
    }

    @GetMapping("/user/getAll")
    public List<UserDto> getAllUsers(Principal principal) {
        return service.getAllUsers();
    }
}