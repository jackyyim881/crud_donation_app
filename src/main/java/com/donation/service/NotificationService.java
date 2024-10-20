package com.donation.service;

import com.donation.models.data.Notification;
import com.donation.models.data.User;
import java.util.List;

public interface NotificationService {
    void createNotification(User user, String message);

    List<Notification> getNotificationsForUser(User user);

    void markAsRead(Long notificationId);
}
