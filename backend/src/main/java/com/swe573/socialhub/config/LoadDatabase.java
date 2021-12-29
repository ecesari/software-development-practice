package com.swe573.socialhub.config;

import com.swe573.socialhub.domain.Service;
import com.swe573.socialhub.domain.Tag;
import com.swe573.socialhub.domain.User;
import com.swe573.socialhub.domain.UserServiceApproval;
import com.swe573.socialhub.domain.key.UserServiceApprovalKey;
import com.swe573.socialhub.enums.ApprovalStatus;
import com.swe573.socialhub.repository.ServiceRepository;
import com.swe573.socialhub.repository.TagRepository;
import com.swe573.socialhub.repository.UserRepository;
import com.swe573.socialhub.repository.UserServiceApprovalRepository;
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
    CommandLineRunner initDatabase(TagRepository tagRepository, UserRepository userRepository, ServiceRepository serviceRepository, UserServiceApprovalRepository approvalRepository, PasswordEncoder passwordEncoder) {

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
                    30, user2,
                    40.98713967228238, 28.839091492848105,
                    new HashSet<Tag>() {{
                        add(tag3);
                    }});

            var service3 = new Service(null,
                    "Eminönü Tour",
                    "Hey everyone! I'm a professional tourist and I would like to give you a tour of Eminönü. We will start and finish at Eminönü Meydan. We will be visiting many historical places as well as bazaars. We will also visit popular restaurants.",
                    "Eminönü, Istanbul",
                    LocalDateTime.of(2022, 3, 15, 12, 0),
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
//            var service4 = new Service(null,
//                    "Pet My Dog",
//                    "Well technically this is a service from my dog but anyways you can come to Maçka Park and pet my cute dog. He won't bite(I can't promise). He's definitely worth your time.",
//                    "Maçka Park, Istanbul",
//                    LocalDateTime.of(2022, 2, 23, 13, 0),
//                    1,
//                    100,
//                    29, user3,
//                    41.045570653598446, 28.993261953340998,
//                    new HashSet<Tag>() {{
//                        add(tag3);
//                        add(tag4);
//                        add(tag5);
//                    }});
            var approval = saveAndGetApproval(approvalRepository, user1, service);
            var approval2 = saveAndGetApproval(approvalRepository, user3, service);
            var approval3 = saveAndGetApproval(approvalRepository, user1, service2);
            var approval4 = saveAndGetApproval(approvalRepository, user3, service2);
            var approval5 = saveAndGetApproval(approvalRepository, user3, service3);
            var approval6 = saveAndGetApproval(approvalRepository, user2, service3);
            var approval7 = saveAndGetApproval(approvalRepository, user1, service4);
            var approval8 = saveAndGetApproval(approvalRepository, user2, service4);

            approvalRepository.findAll().forEach(s -> {
                log.info("Preloaded " + s);
            });

            //endregion


        };
    }

    private UserServiceApproval saveAndGetApproval(UserServiceApprovalRepository approvalRepository, User user1, Service service) {
        var approval = new UserServiceApproval(new UserServiceApprovalKey(user1.getId(), service.getId()), user1, service, ApprovalStatus.PENDING);
        approvalRepository.save(approval);
        return approval;
    }
}