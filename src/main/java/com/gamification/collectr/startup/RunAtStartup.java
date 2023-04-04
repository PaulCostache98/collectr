package com.gamification.collectr.startup;

import com.gamification.collectr.entity.MyUser;
import com.gamification.collectr.entity.Quest;
import com.gamification.collectr.entity.Role;
import com.gamification.collectr.entity.Badge;
import com.gamification.collectr.repository.BadgeRepository;
import com.gamification.collectr.service.BadgeService;
import com.gamification.collectr.service.QuestService;
import com.gamification.collectr.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

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
        quest.setQuestType("Daily");
        questService.saveQuest(quest);

        Badge badge = new Badge();
        badge.setBadgeName("Trivia Master Bronze");
        badge.setId(1L);
        badge.setImgSource("https://w7.pngwing.com/pngs/919/532/png-transparent-bronze-medal-bronze-medal-gold-medal-medal-medal-gold-material-thumbnail.png");

        myUser.setQuests(Set.of(quest));
        myUser.setBadges(Set.of(badge));
        quest.setUsers(Set.of(myUser));
        badge.setUsers(Set.of(myUser));
        badgeService.saveBadge(badge);
        questService.saveQuest(quest);
        userService.saveUser(myUser);


    }
}
