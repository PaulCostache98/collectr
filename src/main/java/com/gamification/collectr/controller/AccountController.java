package com.gamification.collectr.controller;

import com.gamification.collectr.entity.Badge;
import com.gamification.collectr.entity.Game;
import com.gamification.collectr.entity.MyUser;
import com.gamification.collectr.entity.Quest;
import com.gamification.collectr.service.BadgeService;
import com.gamification.collectr.service.GameService;
import com.gamification.collectr.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class AccountController {

    @Autowired
    UserService userService;

    @Autowired
    GameService gameService;

    @Autowired
    BadgeService badgeService;

    @RequestMapping("/account")
    public String account(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyUser user = userService.findUserByUserName(auth.getName());
        Set<Quest> quests = new HashSet<>();
        for (Quest q : user.getQuests()) {
            if(q.getCompleted() != null && q.getCompleted().contains(user.getId()))
            {
                quests.add(q);
            }
        }
        Set<Quest> activeQuests = new HashSet<>(user.getQuests());
        activeQuests.removeAll(quests);
        List<Badge> ongoingBadges = badgeService.findAll();
        ongoingBadges = ongoingBadges.stream().filter(b -> !b.getUsers().contains(user)).toList();
//        List<String> badgeGames = new ArrayList<>(gameService.findAll().stream().filter(g -> !g.getBadges().stream().filter(b -> b.getUsers().contains(user)).toList().isEmpty()).toList()
//                .stream().map(Game::getName).toList());
        model.addAttribute("quests", quests);
        model.addAttribute("activeQuests", activeQuests);
        model.addAttribute("user", user.getFullName());
        model.addAttribute("userDetails", user);
        model.addAttribute("userTokens", user.getUserTokens());
        model.addAttribute("userBadges", user.getBadges());
        model.addAttribute("ongoingBadges", ongoingBadges);

        return "account";
    }
}
