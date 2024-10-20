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
import com.donation.service.NotificationService;
import com.donation.service.UserService;
import com.donation.models.data.Notification;

import java.security.Principal;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/dashboard")
public class NotificationWebController {

    private static final Logger logger = LoggerFactory.getLogger(NotificationWebController.class);
    private final UserService userService;
    private final NotificationService notificationService;

    public NotificationWebController(UserService userService, NotificationService notificationService) {
        this.userService = userService;
        this.notificationService = notificationService;
    }

    @GetMapping("/notifications")
    public String getNotificationsPage(Principal principal, Model model) {
        if (principal == null) {
            logger.warn("Unauthenticated access attempt to /dashboard/notifications");
            return "redirect:/auth/login"; // Redirect to login if not authenticated
        }

        String username = principal.getName();
        logger.info("Fetching notifications for user: {}", username);

        User user = userService.findByUsername(username);

        if (user == null) {
            logger.error("User not found for username: {}", username);
            return "redirect:/auth/login"; // Or redirect to an error page
        }

        List<Notification> notifications = notificationService.getNotificationsForUser(user);
        logger.debug("User {} has {} notifications", username, notifications.size());

        model.addAttribute("notifications", notifications);

        return "dashboard/notification/index"; // Ensure this Thymeleaf template

    }

    @PostMapping("/mark-as-read/{notificationId}")
    @ResponseBody
    public String markAsRead(@PathVariable Long notificationId) {
        notificationService.markAsRead(notificationId);
        return "success";
    }

}
