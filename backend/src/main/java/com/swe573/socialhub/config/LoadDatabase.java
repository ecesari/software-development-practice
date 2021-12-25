package com.swe573.socialhub.config;

import com.swe573.socialhub.domain.Service;
import com.swe573.socialhub.repository.ServiceRepository;
import com.swe573.socialhub.domain.Tag;
import com.swe573.socialhub.domain.User;
import com.swe573.socialhub.repository.TagRepository;
import com.swe573.socialhub.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.HashSet;

@Configuration
class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(TagRepository tagRepository, UserRepository userRepository, ServiceRepository serviceRepository) {

        return args -> {

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

              var user1 = new User(null, "miranda", "miranda.osborne@gmail.com","A human. Being.", new HashSet<Tag>() {{
                  add(tag2);
                  add(tag5);}} );


            var user2 = new User(null, "joshua","joshua.osborne@gmail.com","Life's uncertain. Eat dessert first.", new HashSet<Tag>() {{
                add(tag4);
                add(tag3);
                add(tag1);
            }} );
            var user3 = new User(null,"jane", "jane.austen@gmail.com","Probably the best TV binge-watcher you’ll ever find.",    new HashSet<Tag>() {{
                add(tag4);
                add(tag5);}} );
            userRepository.save(user1);
            userRepository.save(user2);
            userRepository.save(user3);
//
            userRepository.findAll().forEach(user -> {
                log.info("Preloaded " + user);
            });



            var service = new Service(null,
                    "Film Analysis",
                    "I will be teaching film analysis. This is a service that is open to people who do not have any experience in film analysis",
                    "SineBU, Boğaziçi University, Istanbul",
                    LocalDateTime.of(2022,1,10,18,0),
                    120,
                    20,
                    user2
                    );

            var service2 = new Service(null,
                    "Football!",
                    "I will be teaching how to play football! We can have a small match afterwards as well.",
                    "Istanbul",
                    LocalDateTime.of(2022,2,20,20,0),
                    60,
                    10,
                    user2
            );

            var service3 = new Service(null,
                    "Eminönü Tour",
                    "Hey everyone! I'm a professional tourist and I would like to give you a tour of Eminönü. We will start and finish at Eminönü Meydan. We will be visiting many historical places as well as bazaars. We will also visit popular restaurants.",
                    "Eminönü, Istanbul",
                    LocalDateTime.of(2022,3,15,12,0),
                    180,
                    10,
                    user1
            );

            var service4 = new Service(null,
                    "Pet My Dog",
                    "Well technically this is a service from my dog but anyways you can come to Maçka Park and pet my cute dog. He won't bite(I can't promise). He's definitely worth your time.",
                    "Maçka Park, Istanbul",
                    LocalDateTime.of(2022,2,23,13,0),
                    30,
                    100,
                    user3
            );

            serviceRepository.save(service);
            serviceRepository.save(service2);
            serviceRepository.save(service3);
            serviceRepository.save(service4);

            serviceRepository.findAll().forEach(s -> {
                log.info("Preloaded " + s);
            });

        };
    }
}