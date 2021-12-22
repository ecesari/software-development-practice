package com.swe573.socialhub.dto;

import java.util.List;

public class UserDto {
    Long id;
    private String username;
    private String email;
    private String bio;
    private String password;
    private List<Long> tags;

    public List<Long> getTags() {
        return tags;
    }

    public void setTags(List<Long> tags) {
        this.tags = tags;
    }







    public UserDto(Long id, String username, String email, String bio, List<Long> tags) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.bio = bio;
        this.tags = tags;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
