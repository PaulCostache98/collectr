package com.gamification.collectr.controller;

import com.gamification.collectr.entity.MyUser;
import com.gamification.collectr.entity.Quest;
import com.gamification.collectr.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashSet;
import java.util.Set;

@Controller
public class AccountController {

    @Autowired
    UserService userService;

    @RequestMapping("/account")
    public String account(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyUser user = userService.findUserByUserName(auth.getName());
        Set<Quest> quests = new HashSet<>();
        for (Quest q : user.getQuests()) {
            if(q.getCompleted() != null && q.getCompleted().contains(user))
            {
                quests.add(q);
            }
        }
        Set<Quest> activeQuests = new HashSet<>(user.getQuests());
        model.addAttribute("quests", quests);
        model.addAttribute("activeQuests", activeQuests);
        model.addAttribute("user", user.getFullName());
        model.addAttribute("userDetails", user);

        return "account";
    }
}
