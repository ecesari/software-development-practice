package com.swe573.socialhub;


import com.swe573.socialhub.repository.ServiceRepository;
import com.swe573.socialhub.repository.UserRepository;
import com.swe573.socialhub.repository.UserServiceApprovalRepository;
import com.swe573.socialhub.service.NotificationService;
import com.swe573.socialhub.service.UserService;
import com.swe573.socialhub.service.UserServiceApprovalService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.security.auth.Subject;
import java.security.Principal;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApprovalServiceUnitTests {

    @TestConfiguration
    static class NotificationTestContextConfiguration {
        @Bean
        UserService userService() {
            return new UserService();
        }
        @Bean
        NotificationService notificationService()
        {
            return new NotificationService();
        }
        @Bean
        UserServiceApprovalService service()
        {
            return new UserServiceApprovalService();
        }
    }

    @MockBean
    public UserServiceApprovalRepository repository;

    @MockBean
    UserRepository userRepository;

    @MockBean
    ServiceRepository serviceRepository;

    @Autowired
    UserServiceApprovalService service;


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
    public void UserServiceApproval_ShouldThrowError_IfCreditBelowThreshold()
    {
        
    }

}
