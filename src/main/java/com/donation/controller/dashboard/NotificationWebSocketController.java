package com.donation.controller.dashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.donation.repository.UserRepository;
import com.donation.service.NotificationService;

@Controller
public class NotificationWebSocketController {

    private final SimpMessagingTemplate messagingTemplate;
    private final NotificationService notificationService;
    private final UserRepository userRepository;

    @Autowired
    public NotificationWebSocketController(SimpMessagingTemplate messagingTemplate,
            NotificationService notificationService,
            UserRepository userRepository) {
        this.messagingTemplate = messagingTemplate;
        this.notificationService = notificationService;
        this.userRepository = userRepository;
    }

}
