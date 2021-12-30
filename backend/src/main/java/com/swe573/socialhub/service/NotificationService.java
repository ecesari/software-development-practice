package com.swe573.socialhub.service;

import com.swe573.socialhub.domain.Notification;
import com.swe573.socialhub.domain.User;
import com.swe573.socialhub.dto.NotificationDto;
import com.swe573.socialhub.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository repository;

    public void sendNotification(String message, String url, User user) {
        var notification = new Notification(null, message, url, false, user);
        repository.save(notification);
    }

    public NotificationDto mapNotificationToDTO(Notification notification) {
        return new NotificationDto(notification.getId(), notification.getMessage(), notification.getMessageUrl(), notification.getRead());
    }
}
