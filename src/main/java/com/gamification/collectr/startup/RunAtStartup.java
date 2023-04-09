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
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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


    @EventListener(ApplicationReadyEvent.class)
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
//        userService.saveUser(myUser);

        MyUser myUserTwo = new MyUser();
        myUserTwo.setUsername("user1");
        myUserTwo.setId(2L);
        myUserTwo.setPassword("user1");
        myUserTwo.setRandomToken("randomTokenTwo");
        myUserTwo.setRoles(roles);
        myUserTwo.setEnabled(true);
        myUserTwo.setAccountNonExpired(true);
        myUserTwo.setAccountNonLocked(true);
        myUserTwo.setCredentialsNonExpired(true);
        myUserTwo.setEmail("user1@gmail.com");
        myUserTwo.setFullName("Jefferson");
        myUserTwo.setPasswordConfirm("user1");
        myUserTwo.setRandomTokenEmail("randomTokenTwo");
        myUserTwo.setUserTokens(100);

        Quest quest = new Quest();
        quest.setId(1L);
        quest.setQuestName("Daily Trivia I");
        quest.setQuestDescription("Complete 5 Trivia games successfully.");
        quest.setQuestType("Trivia");
        quest.setReward(50);
        quest.setTier(2);
        quest.setDefaultSteps(10);
        quest.setCreatedBy("SYSTEM");
        List<Long> completed = new ArrayList<>();
        List<Integer> steps = new ArrayList<>(List.of(1, -1));
        quest.setCompleted(completed);
        quest.setSteps(steps);
//        questService.saveQuest(quest);

        Badge badge = new Badge();
        badge.setBadgeName("Trivia Master Bronze");
        badge.setId(1L);
        badge.setSteps(0);
        badge.setDefaultSteps(5);
        badge.setImgSource("https://w7.pngwing.com/pngs/919/532/png-transparent-bronze-medal-bronze-medal-gold-medal-medal-medal-gold-material-thumbnail.png");
        badgeService.saveBadge(badge);

        Badge badgeTwo = new Badge();
        badgeTwo.setBadgeName("Mining Master Bronze");
        badgeTwo.setId(2L);
        badgeTwo.setSteps(0);
        badgeTwo.setDefaultSteps(5);
        badgeTwo.setImgSource("https://w7.pngwing.com/pngs/919/532/png-transparent-bronze-medal-bronze-medal-gold-medal-medal-medal-gold-material-thumbnail.png");
        badgeService.saveBadge(badgeTwo);

        Badge badgeTokenTierOne = new Badge();
        badgeTokenTierOne.setBadgeName("Wealth Badge - Tier I");
        badgeTokenTierOne.setId(3L);
        badgeTokenTierOne.setSteps(0);
        badgeTokenTierOne.setCost(100);
        badgeTokenTierOne.setDefaultSteps(-1);
        badgeTokenTierOne.setImgSource("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTIn7VZ3rkdURXL8ahGcueKzZlKOzuGix1aryunqaOx4VfpqZ9n2NEINhNseAb5-DFlnnA&usqp=CAU");
        badgeService.saveBadge(badgeTokenTierOne);

        Badge badgeTokenTierTwo = new Badge();
        badgeTokenTierTwo.setBadgeName("Wealth Badge - Tier II");
        badgeTokenTierTwo.setId(4L);
        badgeTokenTierTwo.setSteps(0);
        badgeTokenTierTwo.setDefaultSteps(-1);
        badgeTokenTierTwo.setCost(200);
        badgeTokenTierTwo.setImgSource("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTIn7VZ3rkdURXL8ahGcueKzZlKOzuGix1aryunqaOx4VfpqZ9n2NEINhNseAb5-DFlnnA&usqp=CAU");
        badgeService.saveBadge(badgeTokenTierTwo);


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
        questTwo.setDefaultSteps(5);
        questTwo.setCompleted(new ArrayList<>());
        questTwo.setCreatedBy("SYSTEM");
        List<Integer> stepsTwo = new ArrayList<>(List.of(1, -1));
        questTwo.setSteps(stepsTwo);

        questService.saveQuest(quest);
        questService.saveQuest(questTwo);
        myUser.setQuests(Set.of(quest, questTwo));
        myUser.setBadges(Set.of(badge));
        userService.saveUser(myUser);
        Set<MyUser> users = new HashSet<>();
        users.add(myUser);
        quest.setUsers(users);
        questTwo.setUsers(users);
        badge.setUsers(Set.of(myUser));
        userService.saveUser(myUser);
        questService.saveQuest(quest);
        game.setBadges(Set.of(badge));
        badge.setGame(game);
        badgeTwo.setGame(gameTwo);
        gameService.saveGame(game);
        badgeService.saveBadge(badgeTwo);
        questService.saveQuest(questTwo);
        badgeService.saveBadge(badge);
        badgeService.saveBadge(badgeTokenTierOne);
        badgeService.saveBadge(badgeTokenTierTwo);
        userService.saveUser(myUserTwo);


        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                List<MyUser> userTemp = userService.findAll();
                userTemp = userTemp.stream().filter(u -> !u.getQuests().isEmpty()).toList();
                Set<Quest> questTemp = new HashSet<>();
                userTemp.forEach(u -> questTemp.addAll(u.getQuests()));
            }
        };
        timer.scheduleAtFixedRate(timerTask, 500, 10000);
    }
}
