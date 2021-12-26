package com.swe573.socialhub.repository;

import com.swe573.socialhub.domain.Service;
import com.swe573.socialhub.domain.User;
import com.swe573.socialhub.domain.UserServiceApproval;
import com.swe573.socialhub.domain.key.UserServiceApprovalKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserServiceApprovalRepository extends JpaRepository<UserServiceApproval, UserServiceApprovalKey> {
    Optional<UserServiceApproval> findUserServiceApprovalByServiceAndUser(Service service, User user);

}