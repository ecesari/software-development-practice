package com.swe573.socialhub.controller;

import com.swe573.socialhub.dto.UserServiceApprovalDto;
import com.swe573.socialhub.service.UserServiceApprovalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/approval")
public class UserServiceApprovalController {

    @Autowired
    private UserServiceApprovalService service;

    @GetMapping("/request/{serviceId}")
    public void App(Principal principal, @PathVariable Long serviceId) {
        try {
            service.RequestApproval(principal,serviceId);
        }
        catch (RuntimeException e)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getLocalizedMessage());
        }
    }

    @GetMapping("/userRequests")
    public ResponseEntity<List<UserServiceApprovalDto>> getApprovalListRequestByUser(Principal principal) {
        try {
            List<UserServiceApprovalDto> services = service.findServiceRequestsByUser(principal);
            return ResponseEntity.ok().body(services);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getLocalizedMessage());
        }
    }



}
