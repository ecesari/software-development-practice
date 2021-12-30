package com.swe573.socialhub.dto;

import java.util.List;

public class UserDto {
    Long id;
    private String username;
    private String email;
    private String bio;
    private String password;
    private List<TagDto> userTags;
    private Integer balance;
    private List<NotificationDto> notifications;



    public UserDto(Long id, String username, String email, String bio, Integer balance, List<NotificationDto> notifications) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.bio = bio;
        this.balance = balance;
        this.notifications = notifications;
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

    public List<TagDto> getUserTags() {
        return userTags;
    }

    public void setUserTags(List<TagDto> userTags) {
        this.userTags = userTags;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public List<NotificationDto> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<NotificationDto> notifications) {
        this.notifications = notifications;
    }
}
