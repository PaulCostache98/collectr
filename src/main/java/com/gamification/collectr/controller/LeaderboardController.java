package com.gamification.collectr.controller;

import com.gamification.collectr.entity.MyUser;
import com.gamification.collectr.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

@Controller
public class LeaderboardController {

    @Autowired
    UserService userService;

    @RequestMapping("/leaderboard")
    public String questsPage(Model model) {
        List<MyUser> users = userService.findAll();

        Comparator<MyUser> byScore = Comparator.comparing(MyUser::getLeaderboardScore).reversed();
        users.sort(byScore);

        model.addAttribute("users", users);
        model.addAttribute("user", userService.findUserByUserName(SecurityContextHolder.getContext().getAuthentication().getName()).getFullName());
        model.addAttribute("userTokens", userService.findUserByUserName(SecurityContextHolder.getContext().getAuthentication().getName()).getUserTokens());

        return "leaderboard";
    }


}
