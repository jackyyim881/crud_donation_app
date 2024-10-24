package com.donation.controller.dashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.donation.models.data.User;

import org.springframework.ui.Model;
import com.donation.service.UserService;

@Controller()
@RequestMapping("/dashboard")
public class CalendarWebController {
    private final UserService UserService;

    @Autowired
    public CalendarWebController(UserService UserService) {
        this.UserService = UserService;
    }

    @RequestMapping("/calendar")
    public String getCalendarPage(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        User currentUser = UserService.findByUsername(userDetails.getUsername());
        model.addAttribute("currentUser", currentUser);
        return "dashboard/calendar/home";
    }

}
