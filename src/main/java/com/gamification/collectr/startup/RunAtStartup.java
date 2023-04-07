package com.gamification.collectr.startup;

import com.gamification.collectr.entity.MyUser;
import com.gamification.collectr.entity.Quest;
import com.gamification.collectr.entity.Role;
import com.gamification.collectr.entity.Badge;
import com.gamification.collectr.entity.Game;
import com.gamification.collectr.repository.BadgeRepository;
import com.gamification.collectr.service.BadgeService;
import com.gamification.collectr.service.GameService;
import com.gamification.collectr.service.QuestService;
import com.gamification.collectr.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class RunAtStartup {

    @Autowired
    private UserService userService;

    @Autowired
    private QuestService questService;

    @Autowired
    private BadgeService badgeService;
    @Autowired
    private BadgeRepository badgeRepository;

    @Autowired
    private GameService gameService;


    @EventListener(ContextRefreshedEvent.class)
    public void contextRefreshedEvent() {

        MyUser myUser = new MyUser();
        myUser.setUsername("user0");
        myUser.setId(1L);
        myUser.setPassword("user0");
        myUser.setRandomToken("randomToken");
        final Role roleUser = new Role("ROLE_USER");
        final Role roleAdmin = new Role("ROLE_ADMIN");
        final Set<Role> roles = new HashSet<>();
        roles.add(roleUser);
        roles.add(roleAdmin);
        myUser.setRoles(roles);
        myUser.setEnabled(true);
        myUser.setAccountNonExpired(true);
        myUser.setAccountNonLocked(true);
        myUser.setCredentialsNonExpired(true);
        myUser.setEmail("user@gmail.com");
        myUser.setFullName("Bahaohha");
        myUser.setPasswordConfirm("user0");
        myUser.setRandomTokenEmail("randomToken");
        myUser.setUserTokens(124);
        userService.saveUser(myUser);

        Quest quest = new Quest();
        quest.setId(1L);
        quest.setQuestName("Daily Trivia I");
        quest.setQuestDescription("Complete 5 Trivia games successfully.");
        quest.setQuestType("Trivia");
        quest.setReward(50);
        quest.setTier(2);
        quest.setCreatedBy("SYSTEM");
        List<Long> completed = new ArrayList<>();
        List<Integer> steps = new ArrayList<>();
        steps.add(1);
        quest.setCompleted(completed);
        quest.setSteps(steps);
        questService.saveQuest(quest);

        Badge badge = new Badge();
        badge.setBadgeName("Trivia Master Bronze");
        badge.setId(1L);
        badge.setImgSource("https://w7.pngwing.com/pngs/919/532/png-transparent-bronze-medal-bronze-medal-gold-medal-medal-medal-gold-material-thumbnail.png");
        badgeService.saveBadge(badge);

        Game game = new Game();
        game.setId(1L);
        game.setName("Trivia");
        game.setType("Trivia");
        game.setImgSource("https://png.pngtree.com/png-vector/20210903/ourmid/pngtree-trivia-poster-png-image_3862027.jpg");
        gameService.saveGame(game);

        Game gameTwo = new Game();
        gameTwo.setId(2L);
        gameTwo.setName("Miner");
        gameTwo.setType("Miner");
        gameTwo.setImgSource("https://cdn-icons-png.flaticon.com/512/8309/8309198.png");
        gameService.saveGame(gameTwo);

        Quest questTwo = new Quest();
        questTwo.setId(2L);
        questTwo.setQuestName("Daily Miner I");
        questTwo.setQuestDescription("Mine 5 rocks.");
        questTwo.setQuestType("Miner");
        questTwo.setReward(10);
        questTwo.setTier(1);
        questTwo.setCreatedBy("SYSTEM");
        List<Long> completedTwo = new ArrayList<>();
        List<Integer> stepsTwo = new ArrayList<>();
        stepsTwo.add(1);
        questTwo.setCompleted(completedTwo);
        questTwo.setSteps(stepsTwo);
        questService.saveQuest(questTwo);


        myUser.setQuests(Set.of(quest, questTwo));
        myUser.setBadges(Set.of(badge));
        quest.setUsers(List.of(myUser));
        badge.setUsers(Set.of(myUser));
        game.setBadges(Set.of(badge));
        badge.setGame(game);
        gameService.saveGame(game);
        badgeService.saveBadge(badge);
        questService.saveQuest(quest);
        userService.saveUser(myUser);


    }
}
