package com.donation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.donation.models.data.Campaign;
import com.donation.models.data.CampaignAmountProjection;
import com.donation.service.CampaignService;
import com.donation.service.DonationService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/campaigns")
public class CampaignWebController {

    @Autowired
    private CampaignService campaignService;

    @Autowired
    private DonationService donationService;

    // 显示所有 Campaign
    @GetMapping
    public String getAllCampaigns(Model model) {
        model.addAttribute("campaigns", campaignService.getAllCampaigns());
        return "campaigns/index";
    }

    @GetMapping("/graph")
    public String getGraph(Model model) {
        model.addAttribute("campaigns", campaignService.getAllCampaigns());
        return "campaigns/graph";
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

    @GetMapping("/campaign-amounts")
    public String showCampaignAmounts(Model model) {
        // Fetch campaign ID and amount data
        List<CampaignAmountProjection> campaignAmounts = donationService.findCampaignIdAndAmount();

        // Add data to the model
        model.addAttribute("campaignAmounts", campaignAmounts);

        return "campaigns/campaign-amounts"; // Returns the Thymeleaf template name
    }
}
