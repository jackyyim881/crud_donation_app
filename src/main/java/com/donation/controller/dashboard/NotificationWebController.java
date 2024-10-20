package com.donation.controller.dashboard;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.donation.models.data.User;
import com.donation.service.UserService;
import com.donation.models.data.Notification;

import java.security.Principal;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/dashboard/notifications")

public class NotificationWebController {
    private NotificationService notificationService;

    @Autowired
    private UserService userService;

    @RequestMapping("")
    public String getNotificationsPage(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        String username = userDetails.getUsername();
        User user = userService.findByUsername(username);

        List<Notification> notifications = notificationService.getAllNotifications(user);
        model.addAttribute("notifications", notifications);

        return "dashboard/notification/index";
    }

    @PostMapping("/mark-as-read/{notificationId}")
    @ResponseBody
    public void markAsRead(@PathVariable Long notificationId) {
        notificationService.markAsRead(notificationId);
    }

}
