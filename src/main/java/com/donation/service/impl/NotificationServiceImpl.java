package com.donation.service.impl;

import com.donation.models.data.Notification;
import com.donation.models.data.User;
import com.donation.repository.NotificationRepository;
import com.donation.service.NotificationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    @Autowired
    public NotificationServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public List<Notification> getUnreadNotifications(User user) {
        return notificationRepository.findByUserAndIsReadFalse(user);
    }

    @Override
    public List<Notification> getAllNotifications(User user) {
        return notificationRepository.findByUser(user);
    }

    @Override
    public Notification createNotification(User user, String message) {
        Notification notification = new Notification(user, message, LocalDateTime.now());
        return notificationRepository.save(notification);
    }

    @Override
    public void markAsRead(Long notificationId) {
        Optional<Notification> optionalNotification = notificationRepository.findById(notificationId);
        optionalNotification.ifPresent(notification -> {
            notification.setRead(true);
            notificationRepository.save(notification);
        });
    }
}
