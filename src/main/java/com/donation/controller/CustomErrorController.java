package com.donation.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController {

    // Define the error path (optional in Spring Boot 2.3 and above)
    private static final String ERROR_PATH = "/error";

    @RequestMapping(ERROR_PATH)
    public String handleError(HttpServletRequest request) {
        // You can retrieve the error status code if needed
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());

            // Handle different status codes if desired
            if (statusCode == 404) {
                // Optionally, redirect to a custom 404 page
                return "redirect:/";
            } else if (statusCode == 500) {
                // Optionally, redirect to a custom 500 page
                return "redirect:/";
            }
        }

        // For all other errors, redirect to the main page
        return "redirect:/";
    }

}