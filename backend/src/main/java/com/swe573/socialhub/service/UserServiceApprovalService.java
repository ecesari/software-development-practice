package com.swe573.socialhub.service;

import com.swe573.socialhub.domain.User;
import com.swe573.socialhub.domain.UserServiceApproval;
import com.swe573.socialhub.enums.ApprovalStatus;
import com.swe573.socialhub.repository.ServiceRepository;
import com.swe573.socialhub.repository.UserRepository;
import com.swe573.socialhub.repository.UserServiceApprovalRepository;
import org.hibernate.exception.DataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;

@Service
public class UserServiceApprovalService {

    @Autowired
    UserServiceApprovalRepository repository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ServiceRepository serviceRepository;

    @Transactional
    public void RequestApproval(Principal principal, Long serviceId)
    {
        final User loggedInUser = userRepository.findUserByUsername(principal.getName()).get();
        if (loggedInUser == null)
            throw new IllegalArgumentException("User doesn't exist.");
        var service = serviceRepository.findById(serviceId).get();
        var entity = new UserServiceApproval(null,loggedInUser,service, ApprovalStatus.PENDING);
        try{
            final UserServiceApproval approval = repository.save(entity);
        }
        catch (Exception e)
        {
            throw new IllegalArgumentException("There was an error trying to send the request");
        }

    }
}
