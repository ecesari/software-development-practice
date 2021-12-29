package com.swe573.socialhub.service;

import com.swe573.socialhub.domain.User;
import com.swe573.socialhub.domain.UserServiceApproval;
import com.swe573.socialhub.domain.key.UserServiceApprovalKey;
import com.swe573.socialhub.dto.ServiceDto;
import com.swe573.socialhub.dto.SimpleApprovalDto;
import com.swe573.socialhub.dto.UserDto;
import com.swe573.socialhub.dto.UserServiceApprovalDto;
import com.swe573.socialhub.enums.ApprovalStatus;
import com.swe573.socialhub.repository.ServiceRepository;
import com.swe573.socialhub.repository.UserRepository;
import com.swe573.socialhub.repository.UserServiceApprovalRepository;
import org.hibernate.exception.DataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class UserServiceApprovalService {

    @Autowired
    UserServiceApprovalRepository repository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ServiceRepository serviceRepository;


    public void RequestApproval(Principal principal, Long serviceId) {
        final User loggedInUser = userRepository.findUserByUsername(principal.getName()).get();
        if (loggedInUser == null)
            throw new IllegalArgumentException("User doesn't exist.");
        var service = serviceRepository.findById(serviceId).get();
        var key = new UserServiceApprovalKey(loggedInUser.getId(), serviceId);
        var entity = new UserServiceApproval(key, loggedInUser, service, ApprovalStatus.PENDING);
        try {
            final UserServiceApproval approval = repository.save(entity);
        } catch (Exception e) {
            throw new IllegalArgumentException("There was an error trying to send the request");
        }

    }

    public List<UserServiceApprovalDto> findServiceRequestsByUser(Principal principal) {
        final User loggedInUser = userRepository.findUserByUsername(principal.getName()).get();
        if (loggedInUser == null)
            throw new IllegalArgumentException("User doesn't exist.");
        var serviceList = repository.findUserServiceApprovalByService_CreatedUserAndApprovalStatus(loggedInUser, ApprovalStatus.PENDING);
        var returnList = new ArrayList<UserServiceApprovalDto>();
        for (UserServiceApproval entity : serviceList) {
            UserServiceApprovalDto dto = getApprovalDto(entity);
            returnList.add(dto);
        }
        return returnList;
    }

    private UserServiceApprovalDto getApprovalDto(UserServiceApproval entity) {
        var service = entity.getService();
        var userDto = new UserDto(entity.getUser().getId(), entity.getUser().getUsername(), entity.getUser().getEmail(), entity.getUser().getBio(), entity.getUser().getBalance());
        var serviceDto = new ServiceDto(service.getId(), service.getHeader(), "", service.getLocation(), service.getTime(), 0, service.getQuota(), service.getAttendingUserCount(), 0L, "", 0.0, 0.0, Collections.emptyList(), service.getStatus());
        var dto = new UserServiceApprovalDto(userDto, serviceDto, entity.getApprovalStatus());
        return dto;
    }


    public void updateRequestStatus(SimpleApprovalDto dto, ApprovalStatus status) {
        var request = repository.findUserServiceApprovalByService_IdAndUser_Id(dto.getServiceId(), dto.getUserId());
        if (!request.isPresent()) {
            throw new IllegalArgumentException("No approval request has been found");
        }
        var entity = request.get();
        entity.setApprovalStatus(status);
        var service = request.get().getService();
        var current = service.getAttendingUserCount();
        service.setAttendingUserCount(current + 1);
//        var user = entity.getUser();
//        var balance = user.getBalance();
//        user.setBalance(balance+ entity.getService().getCredit());
        try {
            var returnData = repository.save(entity);
        } catch (DataException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }
}
