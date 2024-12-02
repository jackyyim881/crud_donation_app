package com.donation.service;

import com.donation.models.data.Notification;

import java.util.List;

public interface NotificationService {

    void sendNotification(Long donorId, Long userId, Long campaignId, String message);

    List<Notification> getNotificationsForUser(Long userId);

    void markAsRead(Long notificationId);
}
