package com.swe573.socialhub.dto;

public class LoginDto {
    Long id;
    private String password;
    private String username;

    public LoginDto() {
    }

    public LoginDto(Long id, String password, String username) {
        this.id = id;
        this.password = password;
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }
}
