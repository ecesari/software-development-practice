package com.swe573.socialhub.service;

import com.swe573.socialhub.domain.User;
import com.swe573.socialhub.dto.*;
import com.swe573.socialhub.enums.ApprovalStatus;
import com.swe573.socialhub.enums.ServiceStatus;
import com.swe573.socialhub.repository.ServiceRepository;
import com.swe573.socialhub.repository.TagRepository;
import com.swe573.socialhub.repository.UserRepository;
import com.swe573.socialhub.repository.UserServiceApprovalRepository;
import com.swe573.socialhub.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.security.sasl.AuthenticationException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private UserServiceApprovalRepository userServiceApprovalRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private NotificationService notificationService;


    @Transactional
    public UserDto register(UserDto dto) {
        final String passwordHash = passwordEncoder.encode(dto.getPassword());
        final User userEntity = new User();
        userEntity.setBio(dto.getBio());
        userEntity.setEmail(dto.getPassword());
        userEntity.setPassword(passwordHash);
        userEntity.setUsername(dto.getUsername());
        userEntity.setBalance(5);
        var tags = dto.getUserTags();
        if (tags != null) {
            for (TagDto tagDto : tags) {
                var addedTag = tagRepository.findById(tagDto.getId());
                if (addedTag.isEmpty()) {
                    throw new IllegalArgumentException("There is no tag with this Id.");
                }
                userEntity.addTag(addedTag.get());
            }
        }


        try {
            final User createdUser = repository.save(userEntity);
            return mapUserToDTO(createdUser);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Username already taken.");
        }
    }

    public UserDto login(UserDto dto) {
        try {
            var userName = dto.getUsername();
            var dbResult = repository.findUserByUsername(userName);
            if (dbResult == null)
                throw new IllegalArgumentException("Invalid username");

            var user = dbResult.get();
            var passwordMatch = passwordEncoder.matches(dto.getPassword(), user.getPassword());

            if (passwordMatch) {
                return mapUserToDTO(user);

            } else {
                throw new IllegalArgumentException("Invalid password");

            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Username already taken.");
        }
    }

    public AuthResponse createAuthenticationToken(AuthRequest authenticationRequest) throws AuthenticationException {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUserName(), authenticationRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new AuthenticationException();
        }


        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUserName());

        final String jwt = jwtTokenUtil.generateToken(userDetails);

        return new AuthResponse(jwt);
    }

    public UserDto getUser(String id, Principal principal) {
        final User loggedInUser = repository.findUserByUsername(principal.getName()).get();
        final Optional<User> userOption = repository.findById(Long.valueOf(id));
        if (userOption.isEmpty())
            throw new IllegalArgumentException("User doesn't exist.");
        return mapUserToDTO(userOption.get());
    }

    public List<UserDto> getAllUsers() {
        final List<User> users = repository.findAll();
        List<UserDto> list = new ArrayList<UserDto>();
        for (User user : users) {
            var dto = mapUserToDTO(user);
            list.add(dto);
        }
        return list;
    }

    public UserDto mapUserToDTO(User user) {
        var notificationList = new ArrayList<NotificationDto>();
        if (user.getNotificationSet() != null) {
            for (var notification : user.getNotificationSet()) {
                var dto = notificationService.mapNotificationToDTO(notification);
                notificationList.add(dto);
            }
        }

        var approvalList = userServiceApprovalRepository.findUserServiceApprovalByUserAndApprovalStatus(user,ApprovalStatus.PENDING);
        var balanceOnHold = approvalList.stream().mapToInt(o -> o.getService().getCredit()).sum();

        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getBio(),
                user.getBalance(),
                notificationList,
                balanceOnHold
        );


    }

    public UserDto getUserByUsername(String userName, Principal principal) {
        final User loggedInUser = repository.findUserByUsername(principal.getName()).get();
        final Optional<User> userOption = repository.findUserByUsername(userName);
        if (userOption.isEmpty())
            throw new IllegalArgumentException("User doesn't exist.");
        return mapUserToDTO(userOption.get());
    }

    public UserDto getUserByPrincipal(Principal principal) {
        final User loggedInUser = repository.findUserByUsername(principal.getName()).get();
        var dto = mapUserToDTO(loggedInUser);
        if (loggedInUser == null)
            throw new IllegalArgumentException("User doesn't exist.");
        return dto;
    }

    public UserServiceDto getUserServiceDetails(Principal principal, Long serviceId) {
        final User loggedInUser = repository.findUserByUsername(principal.getName()).get();
        if (loggedInUser == null)
            throw new IllegalArgumentException("User doesn't exist.");
        var serviceOptional = serviceRepository.findById(serviceId);
        if (serviceOptional == null) {
            throw new IllegalArgumentException("Service doesn't exist.");
        }
        var service = serviceOptional.get();
        var ownsService = service.getCreatedUser().getId() == loggedInUser.getId();
        var userServiceApproval = userServiceApprovalRepository.findUserServiceApprovalByServiceAndUser(service, loggedInUser);
        var dto = new UserServiceDto(userServiceApproval != null && !userServiceApproval.isEmpty(), ownsService);
        return dto;

    }

    public int getBalanceToBe(User user)
    {
        var currentUserCreditsInApprovalState = userServiceApprovalRepository.findUserServiceApprovalByUserAndApprovalStatus(user, ApprovalStatus.PENDING);
        var creditsToRemove = currentUserCreditsInApprovalState.stream().mapToInt(o -> o.getService().getCredit()).sum();
        var activeServices = serviceRepository.findServiceByCreatedUserAndStatus(user, ServiceStatus.ONGOING);
        var creditsToAdd = activeServices.stream().mapToInt(x -> x.getCredit()).sum();
        var currentUserBalance = user.getBalance();
        var balanceToBe = currentUserBalance + creditsToAdd + creditsToRemove;
        return balanceToBe;
    }


}