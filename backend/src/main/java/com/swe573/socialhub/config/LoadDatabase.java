package com.swe573.socialhub.config;

import com.swe573.socialhub.domain.Tag;
import com.swe573.socialhub.repository.TagRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(
//            EmployeeRepository employeeRepository, OrderRepository orderRepository,
            TagRepository tagRepository) {

        return args -> {
//            employeeRepository.save(new Employee("Bilbo", "Baggins", "burglar"));
//            employeeRepository.save(new Employee("Frodo", "Baggins", "thief"));
//
//            employeeRepository.findAll().forEach(employee -> log.info("Preloaded " + employee));
//
//
//            orderRepository.save(new Order("MacBook Pro", Status.COMPLETED));
//            orderRepository.save(new Order("iPhone", Status.IN_PROGRESS));
//
//
//            orderRepository.findAll().forEach(order -> {
//                log.info("Preloaded " + order);
//            });

            tagRepository.save(new Tag("movies"));
            tagRepository.save(new Tag("arts"));
            tagRepository.save(new Tag("sports"));
            tagRepository.save(new Tag("comedy"));
            tagRepository.save(new Tag("misc"));

            tagRepository.findAll().forEach(tag -> {
                log.info("Preloaded " + tag);
            });
        };
    }
}