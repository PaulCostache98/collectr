package com.gamification.collectr.controller;

import com.gamification.collectr.entity.*;
import com.gamification.collectr.exception.UserNotFoundException;
import com.gamification.collectr.service.GameService;
import com.gamification.collectr.service.QuestService;
import com.gamification.collectr.service.UserService;
import com.gamification.collectr.service.BadgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@Controller
public class AdminController {

    @Autowired
    UserService userService;

    @Autowired
    QuestService questService;

    @Autowired
    BadgeService badgeService;

    @Autowired
    GameService gameService;

    @RequestMapping("/admin-page")
    public String adminPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        model.addAttribute("user", userService.findUserByUserName(SecurityContextHolder.getContext().getAuthentication().getName()).getFullName());
        model.addAttribute("userDetails", userService.findUserByUserName(SecurityContextHolder.getContext().getAuthentication().getName()));
        model.addAttribute("users", userService.findAll());
        model.addAttribute("userTokens", userService.findUserByUserName(SecurityContextHolder.getContext().getAuthentication().getName()).getUserTokens());
        model.addAttribute("badges", badgeService.findAll());
        model.addAttribute("quests", questService.findAll());
        model.addAttribute("userService", userService);
        MyUser user = new MyUser();
        return "admin-page";
    }

    @RequestMapping("/delete-user/{id}")
    public String deleteUser(@PathVariable("id") Long id) throws UserNotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(userService.findUserByUserName(authentication.getName()).isAdmin()) {
            MyUser user = userService.findById(id);
            userService.deleteById(id);
        }
        return "redirect:/admin-page";
    }


    @RequestMapping("/update-user/{id}")
    public String updateUser(@PathVariable("id") Long id, Model model) throws UserNotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("user", userService.findUserByUserName(SecurityContextHolder.getContext().getAuthentication().getName()).getFullName());
        model.addAttribute("userTokens", userService.findUserByUserName(SecurityContextHolder.getContext().getAuthentication().getName()).getUserTokens());
        if(userService.findUserByUserName(authentication.getName()).isAdmin()) {
            MyUser user = userService.findById(id);
            model.addAttribute("savedUser", user);
        }

        return "update-user";
    }

    @GetMapping("/save-user")
    public String saveUser(Model model) {
        model.addAttribute("savedUser", new MyUser());
        return "save-user";
    }

    @PostMapping("/save-user")
    public String saveUser(@ModelAttribute MyUser user, Model model) throws UserNotFoundException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(Objects.equals(userService.findUserByUserName(authentication.getName()).getId(), user.getId())) {
            Set<Role> roles = userService.findById(user.getId()).getRoles();
            user.setRoles(roles);
            model.addAttribute("savedUser", user);
            Authentication newAuthentication = new UsernamePasswordAuthenticationToken(user, authentication.getCredentials(), authentication.getAuthorities());
            userService.saveUser(user);

            SecurityContextHolder.getContext().setAuthentication(newAuthentication);
        }
        Set<Role> roles = userService.findById(user.getId()).getRoles();
        user.setRoles(roles);
        model.addAttribute("savedUser", user);
        userService.saveUser(user);
        return "redirect:/admin-page";
    }

    @GetMapping("/add-badge")
    public String addBadge(Model model) {

        Badge badge = new Badge();

        List<Game> games = gameService.findAll();

        model.addAttribute("badge", badge);
        model.addAttribute("games", games);

        return "add-badge";
    }

    @PostMapping(value = "/add-badge")
    public String addBadge(@ModelAttribute("badge") @RequestBody Badge badge) {
        if(badge.getGame() != null) {
            badge.setCost(null);
        }
        else badge.setDefaultSteps(-1);
        List<Integer> stepsTemp = new ArrayList<>();
        userService.findAll().forEach(u -> stepsTemp.add(0));
        badge.setSteps(stepsTemp);
        badgeService.saveBadge(badge);
        return "redirect:/admin-page";
    }

    @GetMapping("/add-quest")
    public String addQuest(Model model) {

        Quest quest = new Quest();
        model.addAttribute("quest", quest);
        model.addAttribute("types", questService.findAll().stream().map(Quest::getQuestType).collect(Collectors.toSet()));

        return "add-quest";
    }

    @PostMapping(value = "/add-quest")
    public String addQuest(@ModelAttribute("quest") @RequestBody Quest quest) {
        quest.setCreatedBy("SYSTEM");
        for(int i = 0;i<userService.findAll().size();i++) {
            quest.getSteps().add(-1);
        }

        questService.saveQuest(quest);
        return "redirect:/admin-page";
    }

    @GetMapping("/add-user")
    public String addUser(Model model) {
        MyUser user = new MyUser();
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setEnabled(true);

        model.addAttribute("user", user);

        return "add-user";
    }

    @PostMapping(value = "/add-user")
    public String addUser(@ModelAttribute("user") @RequestBody MyUser user) {
        user.setRoles(Collections.singleton(new Role("ROLE_USER")));
        userService.saveUser(user);
        return "redirect:/admin-page";
    }

    @RequestMapping("/make-admin/{id}")
    public String makeAdmin(@PathVariable("id") Long id, Model model) throws UserNotFoundException {
        Set<Role> roles = userService.findById(id).getRoles();
        roles.add(new Role("ROLE_ADMIN"));
        MyUser user = userService.findById(id);
        user.setRoles(roles);
        userService.saveUser(user);
        return "redirect:/admin-page";
    }
}
