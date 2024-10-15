package com.donation.service;

import com.donation.models.data.Notification;
import com.donation.models.data.User;

import java.util.List;

public interface NotificationService {

    /**
     * Get a list of unread notifications for a given user.
     * 
     * @param user The user for whom to get unread notifications.
     * @return List of unread notifications.
     */
    List<Notification> getUnreadNotifications(User user);

    /**
     * Get all notifications for a given user.
     * 
     * @param user The user for whom to get all notifications.
     * @return List of all notifications.
     */
    List<Notification> getAllNotifications(User user);

    /**
     * Create a new notification for a given user with the specified message.
     * 
     * @param user    The user to whom the notification belongs.
     * @param message The content of the notification.
     * @return The newly created notification.
     */
    Notification createNotification(User user, String message);

    /**
     * Mark a specific notification as read.
     * 
     * @param notificationId The ID of the notification to mark as read.
     */
    void markAsRead(Long notificationId);
}
