package com.donation.controller.dashboard;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller()
@RequestMapping("/dashboard")
public class CalendarWebController {
    @RequestMapping("/calendar")
    public String getNotifications() {
        return "dashboard/calendar/home";
    }

}
