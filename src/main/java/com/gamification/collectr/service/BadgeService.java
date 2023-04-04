package com.gamification.collectr.service;

import com.gamification.collectr.entity.Badge;
import com.gamification.collectr.exception.BadgeNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BadgeService {

    Badge findById(Long id) throws BadgeNotFoundException;

    Badge findQuestByBadgeName(String name);

    List<Badge> findAll();

    void deleteById(long id);

    Badge saveBadge(Badge badge);

    void updateBadge(Badge badge);

    List<Badge> searchBadges(String keyword);
}
