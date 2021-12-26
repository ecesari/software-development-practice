package com.swe573.socialhub.controller;

import com.swe573.socialhub.service.UserServiceApprovalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

@RestController
@RequestMapping("/approval")

public class UserServiceApprovalController {

    @Autowired
    private UserServiceApprovalService userServiceApprovalService;

    @PostMapping("/request")
    public void request(Principal principal, @PathVariable Long serviceId) {
        try {
            userServiceApprovalService.RequestApproval(principal,serviceId);
        }
        catch (RuntimeException e)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getLocalizedMessage());
        }
    }

}
