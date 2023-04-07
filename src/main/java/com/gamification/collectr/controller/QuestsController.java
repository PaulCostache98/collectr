package com.gamification.collectr.controller;

import com.gamification.collectr.entity.MyUser;
import com.gamification.collectr.entity.Quest;
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

    @RequestMapping("/quests")
    public String questsPage(Model model) {
        List<Quest> quests = questService.findAll();
        for (Quest q : quests) {
            if (!(q.getCompleted() == null) && q.getCompleted().contains(userService.findUserByUserName(SecurityContextHolder.getContext().getAuthentication().getName()).getId())) {
                model.addAttribute("completed", "Completed");
            } else model.addAttribute("completed", "Not Completed");
        }

        model.addAttribute("questList", quests);
        model.addAttribute("user", userService.findUserByUserName(SecurityContextHolder.getContext().getAuthentication().getName()).getFullName());
        model.addAttribute("userTokens", userService.findUserByUserName(SecurityContextHolder.getContext().getAuthentication().getName()).getUserTokens());
        return "quests";
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
        userService.saveUser(user);
        questService.saveQuest(quest);
        return "redirect:/quests";
    }

}
