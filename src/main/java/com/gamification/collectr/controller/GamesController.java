package com.gamification.collectr.controller;

import com.gamification.collectr.entity.Game;
import com.gamification.collectr.service.GameService;
import com.gamification.collectr.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class GamesController {

    @Autowired
    GameService gameService;

    @Autowired
    UserService userService;

    @RequestMapping("/games")
    String games(Model model) {

        List<Game> games = gameService.findAll();
        model.addAttribute("games", games);
        model.addAttribute("user", userService.findUserByUserName(SecurityContextHolder.getContext().getAuthentication().getName()).getFullName());
        model.addAttribute("userTokens", userService.findUserByUserName(SecurityContextHolder.getContext().getAuthentication().getName()).getUserTokens());
        return "games";
    }

}
