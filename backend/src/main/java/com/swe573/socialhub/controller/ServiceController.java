package com.swe573.socialhub.controller;

import com.swe573.socialhub.dto.ServiceDto;
import com.swe573.socialhub.service.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/service")

public class ServiceController {

    @Autowired
    private ServiceService serviceService;

    @GetMapping
    public List<ServiceDto> findAllServices() {
        try {
            return serviceService.findAllServices();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getLocalizedMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceDto> findServiceById(@PathVariable(value = "id") long id) {
        try {
            Optional<ServiceDto> service = serviceService.findById(id);

            if (service.isPresent()) {
                return ResponseEntity.ok().body(service.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getLocalizedMessage());
        }
    }

    @GetMapping("/userService")
    public ResponseEntity<List<ServiceDto>> getListByUser(Principal principal) {
        try {
            List<ServiceDto> services = serviceService.findByUser(principal);
            return ResponseEntity.ok().body(services);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getLocalizedMessage());
        }
    }

    @PostMapping

    public ResponseEntity<Long> saveService(Principal principal, @Validated @RequestBody ServiceDto service) {
        try {
            var result = serviceService.save(principal, service);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getLocalizedMessage());
        }
    }
}
