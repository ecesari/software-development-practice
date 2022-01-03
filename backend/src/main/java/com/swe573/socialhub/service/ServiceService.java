package com.swe573.socialhub.service;

import com.swe573.socialhub.domain.Service;
import com.swe573.socialhub.domain.Tag;
import com.swe573.socialhub.domain.User;
import com.swe573.socialhub.domain.UserServiceApproval;
import com.swe573.socialhub.dto.ServiceDto;
import com.swe573.socialhub.dto.TagDto;
import com.swe573.socialhub.enums.ApprovalStatus;
import com.swe573.socialhub.enums.ServiceFilter;
import com.swe573.socialhub.enums.ServiceStatus;
import com.swe573.socialhub.repository.ServiceRepository;
import com.swe573.socialhub.repository.TagRepository;
import com.swe573.socialhub.repository.UserRepository;
import com.swe573.socialhub.repository.UserServiceApprovalRepository;
import org.hibernate.exception.DataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
public class ServiceService {

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private UserServiceApprovalRepository approvalRepository;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private UserService userService;

    public List<ServiceDto> findAllServices() {
        var entities = serviceRepository.findAll();
        entities = entities.stream().filter(x-> x.getTime().isAfter(LocalDateTime.now())).limit(3).collect(Collectors.toUnmodifiableList());

        var list = entities.stream().map(service -> mapToDto(service)).collect(Collectors.toUnmodifiableList());


        return list;
    }

    public List<ServiceDto> findAllServices(Principal principal,Boolean getOngoingOnly, ServiceFilter filter) {
        var entities = serviceRepository.findAll();
        final User loggedInUser = userRepository.findUserByUsername(principal.getName()).get();

        if (getOngoingOnly)
        {
            entities = entities.stream().filter(x-> x.getTime().isAfter(LocalDateTime.now())).collect(Collectors.toUnmodifiableList());
        }
        switch(filter) {
            case createdByUser:
                entities = entities.stream().filter(x->x.getCreatedUser() == loggedInUser).collect(Collectors.toUnmodifiableList());
                break;
            case first3:
                entities = entities.stream().limit(3).collect(Collectors.toUnmodifiableList());
                break;
            case attending:
                entities = entities.stream().filter(x->x.getApprovalSet().stream().anyMatch(y->y.getUser() == loggedInUser && y.getApprovalStatus() == ApprovalStatus.APPROVED)).collect(Collectors.toUnmodifiableList());

                break;
            default:
                // code block
        }

        var list = entities.stream().map(service -> mapToDto(service)).collect(Collectors.toUnmodifiableList());


        return list;
    }

    public Optional<ServiceDto> findById(Long id) {

        Optional<Service> service = serviceRepository.findById(id);

        if (service.isPresent()) {
            var dto = mapToDto(service.get());
            return Optional.ofNullable(dto);
        } else {
            throw new IllegalArgumentException("No services have been found");
        }
    }

    @Transactional
    public Long save(Principal principal, ServiceDto dto) {
        //check token => if username is null, throw an error
        final User loggedInUser = userRepository.findUserByUsername(principal.getName()).get();
        if (loggedInUser == null)
            throw new IllegalArgumentException("User doesn't exist.");

        try {
            var entity = mapToEntity(dto);
            entity.setCreatedUser(loggedInUser);

            var tags = dto.getServiceTags();
            if (tags != null) {
                for (TagDto tagDto : tags) {
                    var addedTag = tagRepository.findById(tagDto.getId());
                    if (addedTag.isEmpty()) {
                        throw new IllegalArgumentException("There is no tag with this Id.");
                    }
                    entity.addTag(addedTag.get());
                }
            }
            //check pending credits and balance if the sum is above 20 => throw an error
            var currentUserBalance = userService.getBalanceToBe(loggedInUser);
            var balanceToBe = currentUserBalance + dto.getMinutes();
            if (balanceToBe >= 20)
                throw new IllegalArgumentException("You have reached the maximum limit of credits. You cannot create a service before spending your credits.");


            var savedEntity = serviceRepository.save(entity);
            return savedEntity.getId();
        } catch (DataException e) {
            throw new IllegalArgumentException("There was a problem trying to save service to db");
        }


    }


    public List<ServiceDto> findByUser(Principal principal) {
        final User loggedInUser = userRepository.findUserByUsername(principal.getName()).get();
        if (loggedInUser == null)
            throw new IllegalArgumentException("User doesn't exist.");
        try {
            var entities = serviceRepository.findServiceByCreatedUser(loggedInUser);
            var dtoList = entities.stream().map(service -> mapToDto(service)).collect(Collectors.toUnmodifiableList());
            return dtoList;
        } catch (DataException e) {
            throw new IllegalArgumentException("There was a problem trying to save service to db");
        }

    }


    @Transactional
    public void complete(Principal principal, Long serviceId) {
        final User loggedInUser = userRepository.findUserByUsername(principal.getName()).get();
        if (loggedInUser == null)
            throw new IllegalArgumentException("User doesn't exist.");
        Optional<Service> service = serviceRepository.findById(serviceId);

        if (service.isPresent()) {
            var entity = service.get();
            entity.setStatus(ServiceStatus.APPROVED);

            var pendingUserRequests = approvalRepository.findUserServiceApprovalByService_IdAndApprovalStatus(serviceId, ApprovalStatus.PENDING);
            for (UserServiceApproval pendingUserRequest : pendingUserRequests) {
                pendingUserRequest.setApprovalStatus(ApprovalStatus.DENIED);
                notificationService.sendNotification("Your request for service " + entity.getHeader() + " has been denied.",
                        "/service/" + entity.getId(), pendingUserRequest.getUser());
            }
            var approvedUserRequests = approvalRepository.findUserServiceApprovalByService_IdAndApprovalStatus(serviceId, ApprovalStatus.PENDING);
            for (UserServiceApproval approvedUserRequest : approvedUserRequests) {
                var balance = approvedUserRequest.getUser().getBalance();
                approvedUserRequest.getUser().setBalance(balance - approvedUserRequest.getService().getCredit());
                notificationService.sendNotification(String.format("Your request for service " + entity.getHeader()+ " has been approved."),
                        "/service/" + entity.getId(), approvedUserRequest.getUser());
            }
            var createdUser = service.get().getCreatedUser();
            var createdUserBalance = createdUser.getBalance();
            createdUser.setBalance(createdUserBalance + service.get().getCredit());


        } else {
            throw new IllegalArgumentException("No services have been found");
        }

    }

    private ServiceDto mapToDto(Service service) {
        var list = new ArrayList<TagDto>();
        if (service.getServiceTags() != null) {
            for (Tag tag : service.getServiceTags()) {
                var dto = new TagDto(tag.getId(), tag.getName());
                list.add(dto);
            }
        }
        var approvals = service.getApprovalSet();
        var attending = approvals.stream().filter(x-> x.getApprovalStatus() == ApprovalStatus.APPROVED).count();
        var pending = approvals.stream().filter(x-> x.getApprovalStatus() == ApprovalStatus.PENDING).count();
        return new ServiceDto(service.getId(), service.getHeader(), service.getDescription(), service.getLocation(), service.getTime(), service.getCredit(), service.getQuota(), attending, service.getCreatedUser().getId(), service.getCreatedUser().getUsername(), service.getLatitude(), service.getLongitude(), list, service.getStatus(), pending);
    }


    private Service mapToEntity(ServiceDto dto) {
        return new Service(null, dto.getHeader(), dto.getDescription(), dto.getLocation(), dto.getTime(), dto.getMinutes(), dto.getQuota(), 0, null, dto.getLatitude(), dto.getLongitude(), null);
    }


}
