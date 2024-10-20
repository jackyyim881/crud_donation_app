package com.donation.service.impl;

import com.donation.models.data.Notification;
import com.donation.models.data.User;
import com.donation.repository.NotificationRepository;
import com.donation.service.NotificationService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public void createNotification(User user, String message) {
        Notification notification = new Notification(user, message, LocalDateTime.now());
        notificationRepository.save(notification);
    }

    @Override
    public List<Notification> getNotificationsForUser(User user) {
        return notificationRepository.findByUserOrderByTimestampDesc(user);
    }

    @Override
    public void markAsRead(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid notification ID"));
        notification.setRead(true);
        notificationRepository.save(notification);
    }

}
