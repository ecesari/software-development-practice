package com.swe573.socialhub.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class User {
    private @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    private String username;
    private String email;
    private String bio;
    private  String latitude;
    private  String longitude;
    private String formattedAddress;
    @ManyToMany(cascade = {CascadeType.MERGE})
    @JoinTable(
            name = "user_tags",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "tag_id")}
    )
    private Set<Tag> userTags;
    @OneToMany(mappedBy = "createdUser")
    private Set<Service> createdServices;
    @OneToMany(mappedBy = "user")
    Set<UserServiceApproval> approvalSet;
    private Integer balance;
    @OneToMany(mappedBy = "receiver")
    Set<Notification> notificationSet;
    @OneToMany(mappedBy = "followingUser")
    Set<UserFollowing> followingUsers;
    @OneToMany(mappedBy = "followedUser")
    Set<UserFollowing> followedBy;

    public User(Long id, String username, String email, String bio, Set<Tag> userTags, Integer balance, String latitude, String longitude, String formattedAddress) {
        this.id = id;
        this.bio = bio;
        this.username = username;
        this.email = email;
        this.latitude = latitude;
        this.longitude = longitude;
        this.formattedAddress = formattedAddress;
        this.userTags = userTags;
        this.balance = balance;
    }

    public User() {

    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String password;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public Set<Tag> getTags() {
        if (this.userTags == null) {
            this.userTags = new HashSet<Tag>();
        }
        return userTags;
    }

    public void setTags(Set<Tag> userTags) {
        this.userTags = userTags;
    }

    public void addTag(Tag tag) {
        if (this.userTags == null) {
            this.userTags = new HashSet<Tag>();
        }
        this.userTags.add(tag);
    }

    public Set<Service> getCreatedServices() {
        return createdServices;
    }

    public void setCreatedServices(Set<Service> createdServices) {
        this.createdServices = createdServices;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public Set<Tag> getUserTags() {
        return userTags;
    }

    public void setUserTags(Set<Tag> userTags) {
        this.userTags = userTags;
    }

    public Set<UserServiceApproval> getApprovalSet() {
        return approvalSet;
    }

    public void setApprovalSet(Set<UserServiceApproval> approvalSet) {
        this.approvalSet = approvalSet;
    }

    public Set<Notification> getNotificationSet() {
        return notificationSet;
    }

    public void setNotificationSet(Set<Notification> notificationSet) {
        if (this.notificationSet == null) {
            this.notificationSet = new HashSet<Notification>();
        }
        this.notificationSet = notificationSet;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getFormattedAddress() {
        return formattedAddress;
    }

    public void setFormattedAddress(String formattedAddress) {
        this.formattedAddress = formattedAddress;
    }

    public Set<UserFollowing> getFollowingUsers() {
        if (followingUsers == null)
            setFollowingUsers(new HashSet<>());
        return followingUsers;
    }

    public void setFollowingUsers(Set<UserFollowing> followingUsers) {
        this.followingUsers = followingUsers;

    }

    public Set<UserFollowing> getFollowedBy() {
        if (followedBy == null)
            setFollowedBy(new HashSet<>());
        return followedBy;
    }

    public void setFollowedBy(Set<UserFollowing> followedBy) {
        this.followedBy = followedBy;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + this.id + ", username='" + this.username + '\'' + '}';
    }
}
