package com.swe573.socialhub;

import com.swe573.socialhub.domain.Service;
import com.swe573.socialhub.domain.User;
import com.swe573.socialhub.domain.UserServiceApproval;
import com.swe573.socialhub.domain.key.UserServiceApprovalKey;
import com.swe573.socialhub.enums.ApprovalStatus;
import com.swe573.socialhub.enums.ServiceStatus;
import com.swe573.socialhub.repository.ServiceRepository;
import com.swe573.socialhub.repository.TagRepository;
import com.swe573.socialhub.repository.UserRepository;
import com.swe573.socialhub.repository.UserServiceApprovalRepository;
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
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@TestPropertySource(locations = "classpath:application-test.properties")
public class UserServiceUnitTests {

    @TestConfiguration
    static class ServiceServiceUnitTestsConfiguration {
        @Bean
        UserService service()
        {
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
        testUser.setId(2L);
        testUser.setUsername("test user 2");


        var testService1 = new Service();
        testService1.setCredit(ThreadLocalRandom.current().nextInt(1, 10));
        testService1.setId(new Random().nextLong());

        var testService2 = new Service();
        testService2.setCredit(ThreadLocalRandom.current().nextInt(1, 10));
        testService2.setId(new Random().nextLong());

        var testApproval1Key = new UserServiceApprovalKey(testUser.getId(),testService2.getId());
        var testApproval = new UserServiceApproval();
        testApproval.setId(testApproval1Key);
        testApproval.setService(testService2);
        testApproval.setUser(testUser);
        testApproval.setApprovalStatus(ApprovalStatus.PENDING);

        var testServices = new ArrayList<Service>(){{
            add(testService2);
        }};

        var testApprovals = new ArrayList<UserServiceApproval>(){{
            add(testApproval);
        }};


        Mockito.when(userServiceApprovalRepository.findUserServiceApprovalByService_CreatedUserAndApprovalStatus(testUser, ApprovalStatus.PENDING)).thenReturn(testApprovals);
        Mockito.when(serviceRepository.findServiceByCreatedUserAndStatus(testUser, ServiceStatus.ONGOING)).thenReturn(testServices);

        var expectedResult = testUser.getBalance() + testService2.getCredit() + testApproval.getService().getCredit();

        var result = service.getBalanceToBe(testUser);
        assertEquals(expectedResult,result);

    }

}
