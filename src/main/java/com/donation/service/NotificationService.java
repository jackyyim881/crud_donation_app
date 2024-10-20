package com.donation.service;

import com.donation.models.data.Notification;
import com.donation.models.data.User;
import java.util.List;

public interface NotificationService {
    List<Notification> getNotificationsForUser(User user);

    Notification createNotificationForUser(Long userId, String message);

    void markAsRead(Long notificationId);
}
