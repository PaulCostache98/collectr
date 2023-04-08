package com.gamification.collectr.controller;

import com.gamification.collectr.entity.Badge;
import com.gamification.collectr.entity.MyUser;
import com.gamification.collectr.entity.Quest;
import com.gamification.collectr.exception.QuestNotFoundException;
import com.gamification.collectr.service.BadgeService;
import com.gamification.collectr.service.QuestService;
import com.gamification.collectr.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class QuestsController {

    @Autowired
    QuestService questService;

    @Autowired
    UserService userService;

    @Autowired
    BadgeService badgeService;

    @RequestMapping("/quests")
    public String questsPage(Model model) {
        List<Quest> quests = questService.findAll();
        List<Badge> badges = badgeService.findAll();
        model.addAttribute("quests", quests);
        model.addAttribute("badges", badges);
        model.addAttribute("userDetails", userService.findUserByUserName(SecurityContextHolder.getContext().getAuthentication().getName()));
        model.addAttribute("user", userService.findUserByUserName(SecurityContextHolder.getContext().getAuthentication().getName()).getFullName());
        model.addAttribute("userTokens", userService.findUserByUserName(SecurityContextHolder.getContext().getAuthentication().getName()).getUserTokens());
        return "quests";
    }

    @RequestMapping("/quests/buy/{id}")
    public String buyQuest(@PathVariable Integer id, Model model) throws QuestNotFoundException {
        Quest quest = questService.findById((long) id);
        int price = 0;
        switch (quest.getTier()) {
            case 1 -> price = 10;
            case 2 -> price = 50;
            case 3 -> price = 100;
        }
        MyUser user = userService.findUserByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
        user.setUserTokens(user.getUserTokens() - price);
        quest.getSteps().add(quest.getDefaultSteps());
        user.getQuests().add(quest);
        List<MyUser> users = userService.findAll();
        for (MyUser userTemp : users) {
            if(quest.getCreatedBy().equals(userTemp.getFullName())) {
                userTemp.setUserTokens(userTemp.getUserTokens()+price);
                userService.updateUser(userTemp);
            }
        }
        userService.updateUser(user);
        quest.getUsers().add(user);
        questService.updateQuest(quest);

        return "redirect:/quests";
    }

    @GetMapping("/create-quest")
    public String createQuest(Model model) {
        MyUser user = userService.findUserByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
        if (user.getUserTokens() >= 50) {
            Quest quest = new Quest();
            boolean tierTwoCheck = false;
            boolean tierThreeCheck = false;
            model.addAttribute("types", questService.findAll().stream().map(Quest::getQuestType));
            model.addAttribute("quest", quest);
            if(user.getUserTokens() >= 100) {
                tierTwoCheck = true;
            }

            if(user.getUserTokens() >= 150) {
                tierThreeCheck = true;
            }
            model.addAttribute("tierTwoCheck", tierTwoCheck);
            model.addAttribute("tierThreeCheck", tierThreeCheck);
            return "create-quest";
        } else return "create-quest-fail";
    }

    @PostMapping("/create-quest")
    public String createQuest(@ModelAttribute("quest") @RequestBody Quest quest) {
        MyUser user = userService.findUserByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
        quest.setCreatedBy(user.getFullName());
        switch (quest.getTier()) {
            case 1 -> user.setUserTokens(user.getUserTokens() - 50);
            case 2 -> user.setUserTokens(user.getUserTokens() - 100);
            case 3 -> user.setUserTokens(user.getUserTokens() - 150);
        }
        userService.updateUser(user);
        questService.updateQuest(quest);
        return "redirect:/quests";
    }

}
