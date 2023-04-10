package com.gamification.collectr.controller;

import com.gamification.collectr.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
public class IndexController {

    @Autowired
    private UserService userService;

    @RequestMapping({"/", "/index"})
    String index(Model model) {

        if(userService.findUserByUserName(SecurityContextHolder.getContext().getAuthentication().getName()) != null)
        {
            model.addAttribute("user", userService.findUserByUserName(SecurityContextHolder.getContext().getAuthentication().getName()).getFullName());
            model.addAttribute("userTokens", userService.findUserByUserName(SecurityContextHolder.getContext().getAuthentication().getName()).getUserTokens());
        }

        return "index";
    }
}
