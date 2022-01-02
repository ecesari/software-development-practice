package com.swe573.socialhub.domain;

import com.swe573.socialhub.domain.key.UserFollowingKey;

import javax.persistence.*;

@Entity
public class UserFollowing {
    @EmbeddedId
    UserFollowingKey id;

    @ManyToOne
    @MapsId("followingUserId")
    @JoinColumn(name = "following_user_id")
    User followingUser;

    @ManyToOne
    @MapsId("followedUserId")
    @JoinColumn(name = "followed_user_id")
    User followedUser;

    public UserFollowing(UserFollowingKey id, User followingUser, User followedUser) {
        this.id = id;
        this.followingUser = followingUser;
        this.followedUser = followedUser;
    }

    public UserFollowing() {

    }

    // standard constructors, getters, and setters


    public UserFollowingKey getId() {
        return id;
    }

    public void setId(UserFollowingKey id) {
        this.id = id;
    }

    public User getFollowingUser() {
        return followingUser;
    }

    public void setFollowingUser(User followingUser) {
        this.followingUser = followingUser;
    }

    public User getFollowedUser() {
        return followedUser;
    }

    public void setFollowedUser(User followedUser) {
        this.followedUser = followedUser;
    }

    @Override
    public String toString() {
        return "UserServiceApproval{" +
                "followingUser=" + followingUser.getUsername() +
                ", followedUser=" + followedUser.getUsername() +
                '}';
    }
}
