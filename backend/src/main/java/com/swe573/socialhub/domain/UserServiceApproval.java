package com.swe573.socialhub.domain;

import com.swe573.socialhub.domain.key.UserServiceApprovalKey;
import com.swe573.socialhub.enums.ApprovalStatus;

import javax.persistence.*;

@Entity
public class UserServiceApproval {
    @EmbeddedId
    UserServiceApprovalKey id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    User user;

    @ManyToOne
    @MapsId("serviceId")
    @JoinColumn(name = "service_id")
    Service service;

    ApprovalStatus approvalStatus;

    // standard constructors, getters, and setters

}
