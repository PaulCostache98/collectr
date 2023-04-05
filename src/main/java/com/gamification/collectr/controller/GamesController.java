package com.gamification.collectr.controller;

import com.gamification.collectr.entity.Game;
import com.gamification.collectr.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class GamesController {

    @Autowired
    GameService gameService;

    @RequestMapping("/games")
    String games(Model model) {

        List<Game> games = gameService.findAll();
        model.addAttribute("games", games);
        return "games";
    }

}
