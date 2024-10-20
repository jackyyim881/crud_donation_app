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
<<<<<<< HEAD
    public void createNotification(User user, String message) {
        Notification notification = new Notification(user, message, LocalDateTime.now());
=======
    public void createNotification(String message, User user, LocalDateTime timestamp) {
        Notification notification = new Notification(user, message, timestamp);
        notification.setRead(false);
>>>>>>> c20a9643e4d9e22674313fe21adedf9df48d2ec9
        notificationRepository.save(notification);
    }

    @Override
<<<<<<< HEAD
    public List<Notification> getNotificationsForUser(User user) {
        return notificationRepository.findByUserOrderByTimestampDesc(user);
=======
    public List<Notification> getAllNotifications(User user) {
        return notificationRepository.findByUser(user);
    }

    @Override
    public List<Notification> getUnreadNotifications(User user) {
        return notificationRepository.findByUserAndIsReadFalse(user);
>>>>>>> c20a9643e4d9e22674313fe21adedf9df48d2ec9
    }

    @Override
    public void markAsRead(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid notification ID"));
        notification.setRead(true);
        notificationRepository.save(notification);
    }

}
