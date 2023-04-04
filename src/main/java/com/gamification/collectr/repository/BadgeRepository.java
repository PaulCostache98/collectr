package com.gamification.collectr.repository;

import com.gamification.collectr.entity.Badge;
import com.gamification.collectr.entity.Quest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface BadgeRepository extends JpaRepository<Badge, Long> {

    Badge findByBadgeName(String name);

    Badge findByBadgeNameIgnoreCase(String name);

    @Query(
            value = "SELECT * FROM badge b WHERE b.badge_name LIKE %:keyword% OR b.badge_id LIKE %:keyword%",
            nativeQuery = true)
    List<Badge> searchBadges(@Param("keyword") String keyword);

}
