package com.donation.controller.dashboard;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.donation.models.data.User;
import com.donation.service.UserService;

@Controller
@RequestMapping("/dashboard/notifications")
public class NotificationWebController {

    private final UserService userService;

    public NotificationWebController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("")
    public String getNotificationsPage(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        String username = userDetails.getUsername();
        User user = userService.findByUsername(username);

        return "dashboard/notification/index"; // Ensure this view exists
    }

    @PostMapping("/mark-as-read/{notificationId}")
    @ResponseBody
    public void markAsRead(@PathVariable Long notificationId) {
        // notificationService.markAsRead(notificationId);
    }

}
