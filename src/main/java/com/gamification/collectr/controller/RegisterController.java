package com.gamification.collectr.controller;

import com.gamification.collectr.entity.Badge;
import com.gamification.collectr.entity.MyUser;
import com.gamification.collectr.entity.Quest;
import com.gamification.collectr.entity.Role;
import com.gamification.collectr.service.BadgeService;
import com.gamification.collectr.service.QuestService;
import com.gamification.collectr.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Collections;
import java.util.List;

@Controller
public class RegisterController {

    @Autowired
    UserService userService;

    @Autowired
    BadgeService badgeService;

    @Autowired
    QuestService questService;

    @Autowired
    HttpServletRequest request;

    @GetMapping(value = "/register")
    public String registerForm(Model model) {
        MyUser user = new MyUser();
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setEnabled(false);

        model.addAttribute("user", user);

        return "register";
    }

    @PostMapping(value = "/register")
    public String registerUser(@ModelAttribute("user") @RequestBody MyUser user) {
        if (user.getPassword().equals(user.getPasswordConfirm())) {
            request.getSession();
            user.setRoles(Collections.singleton(new Role("ROLE_USER")));
            user.setUserTokens(10);
            userService.saveUser(user);
            List<Badge> badgeTemp = badgeService.findAll();
            badgeTemp.forEach(b -> b.getSteps().add(0));
            badgeTemp.forEach(b -> badgeService.saveBadge(b));
            List<Quest> questTemp = questService.findAll();
            questTemp.forEach(q -> q.getSteps().add(-1));
            questTemp.forEach(q -> questService.saveQuest(q));


            return "register-success";
        } else {
            return "register";
        }
    }

}
