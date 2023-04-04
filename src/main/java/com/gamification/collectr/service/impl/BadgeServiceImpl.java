package com.gamification.collectr.service.impl;

import com.gamification.collectr.entity.Badge;
import com.gamification.collectr.entity.Quest;
import com.gamification.collectr.exception.BadgeNotFoundException;
import com.gamification.collectr.exception.QuestNotFoundException;
import com.gamification.collectr.repository.BadgeRepository;
import com.gamification.collectr.repository.QuestRepository;
import com.gamification.collectr.service.BadgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BadgeServiceImpl implements BadgeService {

    @Autowired
    private final BadgeRepository badgeRepository;

    @Autowired
    public BadgeServiceImpl(BadgeRepository badgeRepository) {
        this.badgeRepository = badgeRepository;
    }
    @Override
    public Badge findById(Long id) throws BadgeNotFoundException {
        return badgeRepository.findById(id).orElseThrow(BadgeNotFoundException::new);
    }

    @Override
    public Badge findQuestByBadgeName(String name) {
        return badgeRepository.findByBadgeNameIgnoreCase(name);
    }

    @Override
    public List<Badge> findAll() {
        return badgeRepository.findAll();
    }

    @Override
    public void deleteById(long id) {
        badgeRepository.deleteById(id);
    }

    @Override
    public Badge saveBadge(Badge receivedBadge) {
        Badge badge = new Badge(receivedBadge);
        return badgeRepository.save(badge);
    }

    @Override
    public void updateBadge(Badge badge) {
        badgeRepository.save(badge);
    }

    @Override
    public List<Badge> searchBadges(String keyword) {
        return badgeRepository.searchBadges(keyword);
    }
}
