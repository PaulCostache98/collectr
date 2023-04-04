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
            if(!(q.getCompleted() == null) && q.getCompleted().contains(userService.findUserByUserName(SecurityContextHolder.getContext().getAuthentication().getName())))
            {
                model.addAttribute("completed", "Completed");
            }
            else model.addAttribute("completed", "Not Completed");
        }

        model.addAttribute("questList", quests);
        model.addAttribute("user", userService.findUserByUserName(SecurityContextHolder.getContext().getAuthentication().getName()).getFullName());
        return "quests";
    }

    @GetMapping("/create-quest")
    public String createQuest(Model model) {
        MyUser user = userService.findUserByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
        if(user.getUserTokens()>=100) {
            user.setUserTokens(user.getUserTokens()-100);
            userService.saveUser(user);
            Quest quest = new Quest();
            model.addAttribute("quest", quest);
            return "create-quest";
        }
        else return "create-quest-fail";
    }

    @PostMapping("/create-quest")
    public String createQuest(@ModelAttribute("quest") @RequestBody Quest quest){
        questService.saveQuest(quest);
        return "redirect:/quests";
    }

}
