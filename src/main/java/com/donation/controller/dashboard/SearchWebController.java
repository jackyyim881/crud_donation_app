package com.donation.controller.dashboard;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dashboard")
public class SearchWebController {

    @GetMapping("/search")
    public String showSearchForm(Model model) {
        return "search/index";
    }
}
