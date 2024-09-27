package com.donation.controller;

import com.donation.models.data.Campaign;
import com.donation.service.CampaignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/campaigns")
public class CampaignWebController {

    @Autowired
    private CampaignService campaignService;

    // 显示所有 Campaign
    @GetMapping
    public String getAllCampaigns(Model model) {
        model.addAttribute("campaigns", campaignService.getAllCampaigns());
        return "campaigns/index";
    }

    // 显示创建 Campaign 的表单
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("campaign", new Campaign());
        return "campaigns/create";
    }

    // 处理创建 Campaign 的请求
    @PostMapping("/create")
    public String createCampaign(@Valid @ModelAttribute("campaign") Campaign campaign) {
        campaignService.createCampaign(campaign);
        return "redirect:/campaigns";
    }

    // 显示编辑 Campaign 的表单
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Campaign campaign = campaignService.getCampaignById(id);
        model.addAttribute("campaign", campaign);
        return "campaigns/edit";
    }

    // 处理更新 Campaign 的请求
    @PostMapping("/edit/{id}")
    public String updateCampaign(@PathVariable Long id, @Valid @ModelAttribute("campaign") Campaign campaignDetails) {
        campaignService.updateCampaign(id, campaignDetails);
        return "redirect:/campaigns";
    }

    // 删除 Campaign
    @GetMapping("/delete/{id}")
    public String deleteCampaign(@PathVariable Long id) {
        campaignService.deleteCampaign(id);
        return "redirect:/campaigns";
    }

    // 查看 Campaign 详情
    @GetMapping("/{id}")
    public String getCampaignById(@PathVariable Long id, Model model) {
        Campaign campaign = campaignService.getCampaignById(id);
        model.addAttribute("campaign", campaign);
        return "campaigns/detail";
    }
}