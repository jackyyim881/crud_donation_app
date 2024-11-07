package com.donation.service.impl;

import com.donation.models.data.Campaign;
import com.donation.models.data.Donor;
import com.donation.models.data.Notification;
import com.donation.models.data.User;
import com.donation.repository.NotificationRepository;
import com.donation.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    @Autowired
    public NotificationServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public void sendNotification(Long donorId, Long userId, Long campaignId, String message) {
        Notification notification = new Notification();

        // Set Donor entity using its ID
        if (donorId != null) {
            Donor donor = new Donor();
            donor.setId(donorId);
            notification.setDonor(donor);
        }

        // Set Campaign entity using its ID
        if (campaignId != null) {
            Campaign campaign = new Campaign();
            campaign.setId(campaignId);
            notification.setCampaign(campaign);
        }

        // Set User entity using its ID
        User user = new User();
        user.setId(userId);
        notification.setUser(user);

        // Set other notification fields
        notification.setMessage(message);
        notification.setTimestamp(LocalDateTime.now());
        notification.setRead(false);

        // Save the notification in the repository
        notificationRepository.save(notification);
    }

    @Override
    public List<Notification> getNotificationsForUser(Long userId) {
        return notificationRepository.findByUserId(userId);
    }

    @Override
    public void markAsRead(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Notification not found"));
        notification.setRead(true);
        notificationRepository.save(notification);
    }
}
