package com.swe573.socialhub.service;

import com.swe573.socialhub.domain.Service;
import com.swe573.socialhub.domain.User;
import com.swe573.socialhub.dto.ServiceDto;
import com.swe573.socialhub.dto.TagDto;
import com.swe573.socialhub.repository.ServiceRepository;
import com.swe573.socialhub.repository.TagRepository;
import com.swe573.socialhub.repository.UserRepository;
import org.hibernate.exception.DataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
public class ServiceService {

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TagRepository tagRepository;

    public List<ServiceDto> findAllServices() {
        var entities = serviceRepository.findAll();

        var list = entities.stream().map(service -> mapToDto(service)).collect(Collectors.toUnmodifiableList());


        return list;
    }

    public Optional<ServiceDto> findById(Long id) {

        Optional<Service> service = serviceRepository.findById(id);

        if (service.isPresent()) {
            var dto = mapToDto(service.get());
            return Optional.ofNullable(dto);
        } else {
            throw new IllegalArgumentException("No services have been found");
        }
    }

    @Transactional
    public Long save(Principal principal, ServiceDto dto) {
        final User loggedInUser = userRepository.findUserByUsername(principal.getName()).get();
        if (loggedInUser == null)
            throw new IllegalArgumentException("User doesn't exist.");

        try {
            var entity = mapToEntity(dto);
            entity.setCreatedUser(loggedInUser);

            var tags = dto.getServiceTags();
            if (tags != null) {
                for (TagDto tagDto : tags) {
                    var addedTag = tagRepository.findById(tagDto.getId());
                    if (addedTag.isEmpty()) {
                        throw new IllegalArgumentException("There is no tag with this Id.");
                    }
                    entity.addTag(addedTag.get());
                }
            }

            var savedEntity = serviceRepository.save(entity);
            return savedEntity.getId();
        } catch (DataException e) {
            throw new IllegalArgumentException("There was a problem trying to save service to db");
        }


    }


    public List<ServiceDto> findByUser(Principal principal) {
        final User loggedInUser = userRepository.findUserByUsername(principal.getName()).get();
        if (loggedInUser == null)
            throw new IllegalArgumentException("User doesn't exist.");
        try {
            var entities = serviceRepository.findServiceByCreatedUser(loggedInUser);
            var dtoList = entities.stream().map(service -> mapToDto(service)).collect(Collectors.toUnmodifiableList());
            return dtoList;
        } catch (DataException e) {
            throw new IllegalArgumentException("There was a problem trying to save service to db");
        }

    }


    private ServiceDto mapToDto(Service service) {
        return new ServiceDto(service.getId(), service.getHeader(), service.getDescription(), service.getLocation(), service.getTime(), service.getMinutes(), service.getQuota(), service.getCreatedUser().getId(), service.getCreatedUser().getUsername(), service.getLatitude(), service.getLongitude(), null);
    }


    private Service mapToEntity(ServiceDto dto) {
        return new Service(null, dto.getHeader(), dto.getDescription(), dto.getLocation(), dto.getTime(), dto.getMinutes(), dto.getQuota(), null,dto.getLatitude(),dto.getLongitude());
    }
}
