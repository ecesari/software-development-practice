package com.swe573.socialhub.service;

import com.swe573.socialhub.domain.User;
import com.swe573.socialhub.dto.*;
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

    @Transactional
    public UserDto register(UserDto params) {
        final String passwordHash = passwordEncoder.encode(params.getPassword());
        final User userEntity = new User();
        userEntity.setBio(params.getBio());
        userEntity.setEmail(params.getPassword());
        userEntity.setPassword(passwordHash);
        userEntity.setUsername(params.getUsername());
        var tags = params.getUserTags();
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

    public UserDto login(UserDto params) {
        try {
            final User user = repository.findUserByUsername(params.getUsername()).get();
            var passwordMatch = passwordEncoder.matches(params.getPassword(), user.getPassword());

            if (passwordMatch) {
                return mapUserToDTO(user);

            } else {
                throw new IllegalArgumentException("Invalid username or password");

            }
        } catch (DataIntegrityViolationException e) {
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

    private UserDto mapUserToDTO(User user) {

        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getBio()
//                user.getTags().stream().map(Tag::getId).collect(Collectors.toUnmodifiableList())
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
        if (serviceOptional == null)
        {
            throw new IllegalArgumentException("Service doesn't exist.");
        }
        var service = serviceOptional.get();
        var ownsService = service.getCreatedUser().getId() == loggedInUser.getId();
        var userServiceApproval = userServiceApprovalRepository.findUserServiceApprovalByServiceAndUser(service,loggedInUser);
        var dto = new UserServiceDto(userServiceApproval != null && !userServiceApproval.isEmpty(),ownsService);
        return dto;

    }
}