package com.gamification.collectr.repository;

import com.gamification.collectr.entity.Quest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuestRepository extends JpaRepository<Quest, Long> {

    Quest findByQuestName(String name);

    Quest findByQuestNameIgnoreCase(String name);

    @Query(
            value = "SELECT * FROM quest q WHERE q.description LIKE %:keyword%", nativeQuery = true)
    List<Quest> findByDescription(@Param("keyword") String keyword);

    @Query(
            value = "SELECT * FROM quest q WHERE q.description LIKE %:keyword% OR q.name LIKE %:keyword% OR q.id LIKE %:keyword% OR q.type LIKE %:keyword%",
            nativeQuery = true)
    List<Quest> searchQuests(@Param("keyword") String keyword);
    Quest findByQuestType(String type);



}
