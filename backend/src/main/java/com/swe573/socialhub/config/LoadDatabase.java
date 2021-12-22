package com.swe573.socialhub.config;

import com.swe573.socialhub.domain.Tag;
import com.swe573.socialhub.domain.User;
import com.swe573.socialhub.repository.TagRepository;
import com.swe573.socialhub.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;

@Configuration
class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(TagRepository tagRepository, UserRepository userRepository) {

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

//            var user1 = new User(null, "miranda", "miranda.osborne@gmail.com", "A human. Being.", null);
//            var tagList1 = new HashSet<Tag>() {{
//                add(tag2);
//                add(tag5);
//            }};
//            user1.setTags(tagList1);

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
        };
    }
}