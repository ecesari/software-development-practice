package com.swe573.socialhub.service;

import com.swe573.socialhub.domain.Notification;
import com.swe573.socialhub.dto.NotificationDto;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

//    @Autowired
//    private TagRepository tagRepository;
//
//    @Autowired
//    private UserRepository userRepository;

//
//    public NotificationDto create() {
//        var entities = tagRepository.findAll();
//        var list = entities.stream().map(tag -> mapToDto(tag)).collect(Collectors.toUnmodifiableList());
//        return list;
//    }


    public NotificationDto mapNotificationToDTO(Notification notification)
    {
        return new NotificationDto(notification.getId(),notification.getMessage(),notification.getMessageUrl(),notification.getRead());
    }
}
