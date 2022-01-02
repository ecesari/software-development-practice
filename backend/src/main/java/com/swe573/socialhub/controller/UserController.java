package com.swe573.socialhub.controller;

import com.swe573.socialhub.domain.UserFollowing;
import com.swe573.socialhub.dto.*;
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

    @PostMapping("/user/setTags")
    public ResponseEntity<AuthResponse> setTags(@RequestBody List<TagDto> params) {
        return null;
    }

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

    @GetMapping("/user")
    public ResponseEntity<UserDto> getUser(Principal principal) {
        try {
            return ResponseEntity.ok().body(service.getUserByPrincipal(principal));
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getLocalizedMessage());
        }
    }


    @GetMapping("/user/{userId}")
    public UserDto getUser(Principal principal, @PathVariable String userId) {
        try {
            return service.getUser(userId, principal);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getLocalizedMessage());
        }
    }

    @GetMapping("/user/getServiceDetails/{serviceId}")
    public UserServiceDto getServiceDetails(Principal principal, @PathVariable Long serviceId) {
        try {
            return service.getUserServiceDetails(principal, serviceId);

        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getLocalizedMessage());
        }
    }


    @GetMapping("/user/getAll")
    public List<UserDto> getAllUsers(Principal principal) {
        return service.getAllUsers();
    }

    @PostMapping("/user/follow/{userId}")
    public ResponseEntity<UserFollowing> followUser(Principal principal, @RequestBody Long userId) {
        try {
            var response = service.follow(principal,userId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getLocalizedMessage());
        }
    }


}