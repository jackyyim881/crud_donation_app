package com.donation.service;

import com.donation.models.data.Notification;
import com.donation.models.data.User;
<<<<<<< HEAD
import java.util.List;

public interface NotificationService {
    void createNotification(User user, String message);

    List<Notification> getNotificationsForUser(User user);
=======
import java.time.LocalDateTime;
import java.util.List;

public interface NotificationService {
    void createNotification(String message, User user, LocalDateTime timestamp);

    List<Notification> getAllNotifications(User user);

    List<Notification> getUnreadNotifications(User user);
>>>>>>> c20a9643e4d9e22674313fe21adedf9df48d2ec9

    void markAsRead(Long notificationId);
}
