package com.donation.controller.dashboard;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/dashboard")
public class GettingStartedWebController {
    @GetMapping("/getting-started")
    public String getGettingStartedPage() {
        return "dashboard/getting-started/index";
    }

}
