package com.donation.controller;

import com.donation.models.data.DonorFeedback;
import com.donation.service.DonorFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/feedbacks")
public class DonorFeedbackWebController {

    @Autowired
    private DonorFeedbackService donorFeedbackService;

    // 显示所有 DonorFeedback
    @GetMapping
    public String getAllFeedbacks(@RequestParam(value = "donorType", required = false) String donorType, Model model) {
        List<DonorFeedback> feedbacks;
        if (donorType != null && !donorType.isEmpty()) {
            feedbacks = donorFeedbackService.getFeedbacksByDonorType(donorType);
            model.addAttribute("selectedDonorType", donorType);
        } else {
            feedbacks = donorFeedbackService.getAllFeedbacks();
        }
        model.addAttribute("feedbacks", feedbacks);
        model.addAttribute("donorTypes", donorFeedbackService.getAllDonorTypes());
        return "feedbacks/index";
    }

    // 显示创建 DonorFeedback 的表单
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("feedback", new DonorFeedback());
        model.addAttribute("donorTypes", donorFeedbackService.getAllDonorTypes());
        return "feedbacks/create";
    }

    // 处理创建 DonorFeedback 的请求
    @PostMapping("/create")
    public String createFeedback(@Valid @ModelAttribute("feedback") DonorFeedback feedback, BindingResult result,
            Model model) {
        if (result.hasErrors()) {
            model.addAttribute("donorTypes", donorFeedbackService.getAllDonorTypes());
            return "feedbacks/create";
        }
        donorFeedbackService.createFeedback(feedback);
        return "redirect:/feedbacks";
    }

    // 显示编辑 DonorFeedback 的表单
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Integer id, Model model) {
        DonorFeedback feedback = donorFeedbackService.getFeedbackById(id);
        model.addAttribute("feedback", feedback);
        model.addAttribute("donorTypes", donorFeedbackService.getAllDonorTypes());
        return "feedbacks/edit";
    }

    // 处理更新 DonorFeedback 的请求
    @PostMapping("/edit/{id}")
    public String updateFeedback(@PathVariable Integer id,
            @Valid @ModelAttribute("feedback") DonorFeedback feedbackDetails, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("donorTypes", donorFeedbackService.getAllDonorTypes());
            return "feedbacks/edit";
        }
        donorFeedbackService.updateFeedback(id, feedbackDetails);
        return "redirect:/feedbacks";
    }

    // 删除 DonorFeedback
    @GetMapping("/delete/{id}")
    public String deleteFeedback(@PathVariable Integer id) {
        donorFeedbackService.deleteFeedback(id);
        return "redirect:/feedbacks";
    }

    // 查看 DonorFeedback 详情
    @GetMapping("/{id}")
    public String getFeedbackById(@PathVariable Integer id, Model model) {
        DonorFeedback feedback = donorFeedbackService.getFeedbackById(id);
        model.addAttribute("feedback", feedback);
        return "feedbacks/detail";
    }
}
