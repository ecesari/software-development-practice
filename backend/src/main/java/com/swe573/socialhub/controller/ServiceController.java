package com.swe573.socialhub.controller;

import com.swe573.socialhub.domain.Service;
import com.swe573.socialhub.domain.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/service")

public class ServiceController {

    @Autowired
    private ServiceRepository serviceRepository;

    @GetMapping
    public List<Service> findAllServices() {
        return serviceRepository.findAll();

    }

    @GetMapping("/{id}")
    public ResponseEntity<Service> findServiceById(@PathVariable(value = "id") long id) {
        Optional<Service> service = serviceRepository.findById(id);

        if(service.isPresent()) {
            return ResponseEntity.ok().body(service.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public Service saveService(@Validated @RequestBody Service service) {
        return serviceRepository.save(service);
    }
}
