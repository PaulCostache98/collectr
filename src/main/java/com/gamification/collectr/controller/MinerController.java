package com.gamification.collectr.controller;

import com.gamification.collectr.entity.MyUser;
import com.gamification.collectr.entity.Quest;
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
            questsTemp.forEach(q -> q.getSteps().set(q.getUsers().indexOf(user), q.getSteps().get(q.getUsers().indexOf(user))-1));
            for (Quest q: questsTemp) {
                if(q.getSteps().get(q.getUsers().indexOf(user)) <= 0)
                {
                    q.getSteps().set(q.getUsers().indexOf(user), 0);
                    q.getCompleted().add(user.getId());
                    user.setUserTokens(user.getUserTokens()+q.getReward());
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
