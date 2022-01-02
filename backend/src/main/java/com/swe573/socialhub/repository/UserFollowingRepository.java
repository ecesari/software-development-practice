package com.swe573.socialhub.repository;

import com.swe573.socialhub.domain.UserFollowing;
import com.swe573.socialhub.domain.key.UserFollowingKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserFollowingRepository extends JpaRepository<UserFollowing, UserFollowingKey> {
}