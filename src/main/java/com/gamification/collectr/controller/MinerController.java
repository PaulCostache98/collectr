package com.gamification.collectr.controller;

import com.gamification.collectr.entity.Badge;
import com.gamification.collectr.entity.MyUser;
import com.gamification.collectr.entity.Quest;
import com.gamification.collectr.service.BadgeService;
import com.gamification.collectr.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Objects;
import java.util.Random;

@Controller
public class MinerController {

    @Autowired
    UserService userService;

    @Autowired
    BadgeService badgeService;

    int clickCount = 0;

    int neededCount = getCount();

    @RequestMapping("/games/2")
    public String miner(Model model) {

        MyUser user = userService.findUserByUserName(SecurityContextHolder.getContext().getAuthentication().getName());

        model.addAttribute("clickCount", clickCount);
        String currentMessage = "You need to hit this rock " +  (neededCount - clickCount) + " more times.";
        if(clickCount == neededCount) {
            currentMessage = "You have finished mining the rock.";
            List<Quest> questsTemp = user.getQuests().stream().filter(q -> Objects.equals(q.getQuestType(), "Miner") && !(!(q.getCompleted() == null) && q.getCompleted().contains(user.getId()))).toList();
            questsTemp.forEach(user.getQuests()::remove);
            questsTemp.forEach(q -> q.getSteps().set(q.getUsers().stream().toList().indexOf(user), q.getSteps().get(q.getUsers().stream().toList().indexOf(user))-1));
            for (Quest q: questsTemp) {
                if(q.getSteps().get(q.getUsers().stream().toList().indexOf(user)) <= 0)
                {
                    q.getSteps().set(q.getUsers().stream().toList().indexOf(user), -1);
                    q.getCompleted().add(user.getId());
                    user.setUserTokens(user.getUserTokens()+q.getReward());
                    List<Badge> badgeTemp = badgeService.findAll().stream().filter(b -> (b.getGame() != null && b.getGame().getType().equals(q.getQuestType()))).toList();
                    badgeTemp.forEach(b -> b.getSteps().set(b.getUsers().stream().toList().indexOf(user), b.getSteps().get(b.getUsers().stream().toList().indexOf(user))+1));
                    List<Badge> badgeCompleted = badgeTemp.stream().filter(b -> Objects.equals(b.getSteps().get(b.getUsers().stream().toList().indexOf(user)), b.getDefaultSteps())).toList();
                    user.getBadges().addAll(badgeCompleted);
                }
            }
            user.getQuests().addAll(questsTemp);
            userService.saveUser(user);
        }
        else clickCount++;
        model.addAttribute("currentMessage", currentMessage);
        return "miner";

    }

    public int getCount() {
        Random random = new Random();
        return random.nextInt(20);
    }
}
