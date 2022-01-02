package com.swe573.socialhub;

import com.swe573.socialhub.domain.Service;
import com.swe573.socialhub.domain.User;
import com.swe573.socialhub.domain.UserFollowing;
import com.swe573.socialhub.domain.UserServiceApproval;
import com.swe573.socialhub.domain.key.UserServiceApprovalKey;
import com.swe573.socialhub.enums.ApprovalStatus;
import com.swe573.socialhub.enums.ServiceStatus;
import com.swe573.socialhub.repository.*;
import com.swe573.socialhub.service.NotificationService;
import com.swe573.socialhub.service.UserService;
import com.swe573.socialhub.util.JwtUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.security.auth.Subject;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@TestPropertySource(locations = "classpath:application-test.properties")
public class UserServiceUnitTests {

    @TestConfiguration
    static class ServiceServiceUnitTestsConfiguration {
        @Bean
        UserService service() {
            return new UserService();
        }
    }

    @Autowired
    private UserService service;

    @MockBean
    private UserRepository repository;

    @MockBean
    private TagRepository tagRepository;

    @MockBean
    private ServiceRepository serviceRepository;

    @MockBean
    private UserServiceApprovalRepository userServiceApprovalRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private JwtUtil jwtTokenUtil;

    @MockBean
    private UserDetailsService userDetailsService;

    @MockBean
    private NotificationService notificationService;

    @MockBean
    private UserFollowingRepository userFollowingRepository;

    class MockPrincipal implements Principal {

        public MockPrincipal(String name) {
            this.name = name;
        }

        final String name;

        @Override
        public String getName() {
            return name;
        }

        @Override
        public boolean implies(Subject subject) {
            return Principal.super.implies(subject);
        }
    }

    @Test
    public void contextLoads() throws Exception {
        assertNotNull(service);
    }

    @Test
    public void Service_GetBalance_ShouldReturn_BalanceToBe() {
        var testUser = new User();
        testUser.setBalance(18);
        testUser.setId(1L);
        testUser.setUsername("test user");

        var testUser2 = new User();
        testUser2.setId(2L);
        testUser2.setUsername("test user 2");


        var testService1 = new Service();
        testService1.setCredit(ThreadLocalRandom.current().nextInt(1, 10));
        testService1.setId(new Random().nextLong());

        var testService2 = new Service();
        testService2.setCredit(ThreadLocalRandom.current().nextInt(1, 10));
        testService2.setId(new Random().nextLong());

        var testApproval1Key = new UserServiceApprovalKey(testUser.getId(), testService2.getId());
        var testApproval = new UserServiceApproval();
        testApproval.setId(testApproval1Key);
        testApproval.setService(testService2);
        testApproval.setUser(testUser);
        testApproval.setApprovalStatus(ApprovalStatus.PENDING);

        var testServices = new ArrayList<Service>() {{
            add(testService2);
        }};

        var testApprovals = new ArrayList<UserServiceApproval>() {{
            add(testApproval);
        }};


        Mockito.when(userServiceApprovalRepository.findUserServiceApprovalByUserAndApprovalStatus(testUser, ApprovalStatus.PENDING)).thenReturn(testApprovals);
        Mockito.when(serviceRepository.findServiceByCreatedUserAndStatus(testUser, ServiceStatus.ONGOING)).thenReturn(testServices);

        var expectedResult = testUser.getBalance() + testService2.getCredit() + testApproval.getService().getCredit();

        var result = service.getBalanceToBe(testUser);
        assertEquals(expectedResult, result);

    }

    @Test
    public void FollowUser_ShouldThrowError_IfAlreadyFollowing() {
        var testUser2 = new User();
        testUser2.setId(2L);
        testUser2.setUsername("test user 2");

        var testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("test user");
        var following = new UserFollowing(testUser, testUser2);

        testUser.setFollowingUsers(new HashSet<>() {{
            add(following);
        }});

        var mockUser = new MockPrincipal(testUser.getUsername());

        Mockito.when(repository.findUserByUsername(testUser.getUsername())).thenReturn(Optional.of(testUser));
        Mockito.when(repository.findUserByUsername(testUser2.getUsername())).thenReturn(Optional.of(testUser2));
        Mockito.when(repository.findUserByUsername(testUser2.getUsername())).thenReturn(Optional.of(testUser2));
        Mockito.when(userFollowingRepository.findUserFollowingByFollowingUserAndFollowedUser(testUser,testUser2)).thenReturn(Optional.of(following));

        assertThrows(IllegalArgumentException.class, () -> service.follow(mockUser, testUser2.getId()));

    }

    @Test
    public void FollowUser_ShouldReturnEntity() {
        var testUser2 = new User();
        testUser2.setId(2L);
        testUser2.setUsername("test user 2");

        var testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("test user");


        var mockUser = new MockPrincipal(testUser.getUsername());

        Mockito.when(repository.findUserByUsername(testUser.getUsername())).thenReturn(Optional.of(testUser));
        Mockito.when(repository.findUserByUsername(testUser2.getUsername())).thenReturn(Optional.of(testUser2));
        Mockito.when(repository.findUserByUsername(testUser2.getUsername())).thenReturn(Optional.of(testUser2));
        Mockito.when(userFollowingRepository.findUserFollowingByFollowingUserAndFollowedUser(testUser,testUser2)).thenReturn(Optional.empty());

        var userFollowing = new UserFollowing(testUser,testUser2);
        userFollowing.setId(1L);
        Mockito.when(userFollowingRepository.save(userFollowing)).thenReturn(userFollowing);

//        Mockito.when(userFollowingRepository.save(userFollowing)).thenCallRealMethod();


        var result = service.follow(mockUser,testUser2.getId());
        assertEquals(testUser, result.getFollowingUser());
        assertEquals(testUser, result.getFollowedUser());

    }

}
