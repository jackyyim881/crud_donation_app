package com.donation.service;

import com.donation.models.data.Notification;
import com.donation.models.data.User;
import java.time.LocalDateTime;
import java.util.List;

public interface NotificationService {
    void createNotification(String message, User user, LocalDateTime timestamp);

    List<Notification> getAllNotifications(User user);

    List<Notification> getUnreadNotifications(User user);

    void markAsRead(Long notificationId);
}
