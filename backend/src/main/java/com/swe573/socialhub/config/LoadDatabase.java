package com.swe573.socialhub.config;

import com.swe573.socialhub.domain.*;
import com.swe573.socialhub.domain.key.UserServiceApprovalKey;
import com.swe573.socialhub.enums.ApprovalStatus;
import com.swe573.socialhub.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.HashSet;

@Configuration
class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(TagRepository tagRepository, UserRepository userRepository, ServiceRepository serviceRepository, UserServiceApprovalRepository approvalRepository, NotificationRepository notificationRepository, PasswordEncoder passwordEncoder) {

        return args -> {


            //region Tags

            var tag1 = new Tag("movies");
            var tag2 = new Tag("arts");
            var tag3 = new Tag("sports");
            var tag4 = new Tag("comedy");
            var tag5 = new Tag("misc");
            tagRepository.save(tag1);
            tagRepository.save(tag2);
            tagRepository.save(tag3);
            tagRepository.save(tag4);
            tagRepository.save(tag5);


            tagRepository.findAll().forEach(tag -> {
                log.info("Preloaded " + tag);
            });

            //endregion

            //region User

            var user1 = new User(null, "miranda", "miranda.osborne@gmail.com", "A human. Being.", new HashSet<Tag>() {{
                add(tag2);
                add(tag5);
            }}, 2);
            user1.setPassword(passwordEncoder.encode("1"));


            var user2 = new User(null, "joshua", "joshua.osborne@gmail.com", "Life's uncertain. Eat dessert first.", new HashSet<Tag>() {{
                add(tag4);
                add(tag3);
                add(tag1);
            }}, 5);
            user2.setPassword(passwordEncoder.encode("1"));

            var user3 = new User(null, "jane", "jane.austen@gmail.com", "Probably the best TV binge-watcher you’ll ever find.", new HashSet<Tag>() {{
                add(tag4);
                add(tag5);
            }}, -1);
            user3.setPassword(passwordEncoder.encode("3"));

            userRepository.save(user1);
            userRepository.save(user2);
            userRepository.save(user3);
//
            userRepository.findAll().forEach(user -> {
                log.info("Preloaded " + user);
            });

            //endregion

            //region Service

            var service = new Service(null,
                    "Film Analysis",
                    "I will be teaching film analysis. This is a service that is open to people who do not have any experience in film analysis",
                    "SineBU, Boğaziçi University, Istanbul",
                    LocalDateTime.of(2022, 1, 10, 18, 0),
                    2,
                    20,
                    8, user2,
                    41.08486331615685,
                    29.033532028860485,
                    new HashSet<Tag>() {{
                        add(tag1);
                        add(tag2);
                    }});


            var service2 = new Service(null,
                    "Football!",
                    "I will be teaching how to play football! We can have a small match afterwards as well.",
                    "Istanbul",
                    LocalDateTime.of(2022, 2, 20, 20, 0),
                    3,
                    10,
                    30, user1,
                    40.98713967228238, 28.839091492848105,
                    new HashSet<Tag>() {{
                        add(tag3);
                    }});

            var service3 = new Service(null,
                    "Eminönü Tour",
                    "Hey everyone! I'm a professional tourist and I would like to give you a tour of Eminönü. We will start and finish at Eminönü Meydan. We will be visiting many historical places as well as bazaars. We will also visit popular restaurants.",
                    "Eminönü, Istanbul",
                    LocalDateTime.of(2021, 12, 15, 12, 0),
                    4,
                    10,
                    10, user1,
                    41.012524056384144, 28.951326923194756,
                    new HashSet<Tag>() {{
                        add(tag5);
                    }});

            var service4 = new Service(null,
                    "Pet My Dog",
                    "Well technically this is a service from my dog but anyways you can come to Maçka Park and pet my cute dog. He won't bite(I can't promise). He's definitely worth your time.",
                    "Maçka Park, Istanbul",
                    LocalDateTime.of(2022, 2, 23, 13, 0),
                    1,
                    100,
                    29, user3,
                    41.045570653598446, 28.993261953340998,
                    new HashSet<Tag>() {{
                        add(tag3);
                        add(tag4);
                        add(tag5);
                    }});

            serviceRepository.save(service);
            serviceRepository.save(service2);
            serviceRepository.save(service3);
            serviceRepository.save(service4);

            serviceRepository.findAll().forEach(s -> {
                log.info("Preloaded " + s);
            });
            //endregion

            //region Approval
            var approval = saveAndGetApproval(approvalRepository, user1, service, ApprovalStatus.APPROVED);
            var approval2 = saveAndGetApproval(approvalRepository, user3, service, ApprovalStatus.APPROVED);
            var approval3 = saveAndGetApproval(approvalRepository, user2, service2, ApprovalStatus.DENIED);
            var approval4 = saveAndGetApproval(approvalRepository, user3, service2, ApprovalStatus.APPROVED);
            var approval5 = saveAndGetApproval(approvalRepository, user3, service3, ApprovalStatus.APPROVED);
            var approval6 = saveAndGetApproval(approvalRepository, user2, service3, ApprovalStatus.PENDING);
            var approval8 = saveAndGetApproval(approvalRepository, user2, service4, ApprovalStatus.APPROVED);
            var approval9 = saveAndGetApproval(approvalRepository, user1, service4, ApprovalStatus.DENIED);

            approvalRepository.findAll().forEach(s -> {
                log.info("Preloaded " + s);
            });

            //endregion

            //region notification

            var notif1 = saveAndGetNotification(userRepository,notificationRepository, "Your Request for Service Film Analysis has been approved.", "/service/" + service.getId(), false, user1);
            var notif2 = saveAndGetNotification(userRepository,notificationRepository, "Your Request for Service Film Analysis has been sent.", "/service/" + service.getId(), false, user1);
            var notif3 = saveAndGetNotification(userRepository,notificationRepository, "Your Service Eminönü Tour's date has passed, don't forget to approve the service!", "/service/" + service3.getId(), false, user1);
            var notif4 = saveAndGetNotification(userRepository,notificationRepository, "Hooray! There is a new request for Eminönü Tour by jane! You can approve or deny this request. ", "/service/" + service3.getId(), false, user1);
            var notif6 = saveAndGetNotification(userRepository,notificationRepository, "Hooray! There is a new request for Football! by joshua! You can approve or deny this request.", "/service/" + service2.getId(), false, user1);
            var notif5 = saveAndGetNotification(userRepository,notificationRepository, "Hooray! There is a new request for Eminönü Tour by joshua! You can approve or deny this request.", "/service/" + service3.getId(), false, user1);
            notificationRepository.findAll().forEach(s -> {
                log.info("Preloaded " + s);
            });
            //endregion

        };
    }

    private Notification saveAndGetNotification(UserRepository userRepository, NotificationRepository notificationRepository, String message, String url, Boolean read, User user) {
        var notification = new Notification(null, message, null, read);
        notificationRepository.save(notification);
        var set = user.getNotificationSet();
        if (set == null) {
            user.setNotificationSet(new HashSet<>());
        }
        set = user.getNotificationSet();
        set.add(notification);
        user.setNotificationSet(set);
        userRepository.save(user);
        return notification;
    }

    private UserServiceApproval saveAndGetApproval(UserServiceApprovalRepository approvalRepository, User user1, Service service, ApprovalStatus approved) {
        var approval = new UserServiceApproval(new UserServiceApprovalKey(user1.getId(), service.getId()), user1, service, approved);
        approvalRepository.save(approval);
        return approval;
    }
}