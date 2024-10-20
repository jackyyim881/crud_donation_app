package com.donation.controller.dashboard;

import com.donation.models.data.Notification;
import com.donation.models.data.User;
import com.donation.service.NotificationService;
import com.donation.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/dashboard")
public class NotificationWebController {
    // logger
    private static final Logger logger = LoggerFactory.getLogger(NotificationWebController.class);

    private final UserService userService;
    private final NotificationService notificationService;

    private RedirectAttributes redirectAttributes;

    public NotificationWebController(UserService userService, NotificationService notificationService) {
        this.userService = userService;
        this.notificationService = notificationService;
    }

    @GetMapping("/notifications")
    public String getNotificationsPage(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        if (userDetails == null) {
            logger.error("UserDetails is null, authentication information is not available.");
            return "redirect:/login"; // Redirect to login if no user details found
        }

        String username = userDetails.getUsername();
        logger.info("Fetching notifications for user with username: {}", username);

        User user = userService.findByUsername(username);
        if (user == null) {
            logger.error("User not found with username: {}", username);
            return "redirect:/dashboard"; // Redirect to dashboard home page
        }

        List<Notification> notifications = notificationService.getNotificationsForUser(user);
        logger.info("Number of notifications found for user {}: {}", username, notifications.size());

        model.addAttribute("notifications", notifications);

        return "dashboard/notification/index"; // Ensure this view exists and displays notifications
    }

    @PostMapping("/create")
    public Notification createNotification(@RequestParam Long userId, @RequestParam String message) {
        return notificationService.createNotificationForUser(userId, message);
    }

    @PostMapping("/mark-as-read/{notificationId}")
    @ResponseBody
    public String markAsRead(@PathVariable Long notificationId) {
        logger.info("Attempting to mark notification with ID {} as read", notificationId);
        try {
            notificationService.markAsRead(notificationId);
            logger.info("Notification {} marked as read successfully", notificationId);
            redirectAttributes.addFlashAttribute("success", "Notification marked as read successfully");
            return "redirect:/dashboard/notifications";
        } catch (Exception e) {
            logger.error("Failed to mark notification as read: {}", e.getMessage());
            return "error";
        }
    }
}
