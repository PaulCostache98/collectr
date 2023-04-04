package com.gamification.collectr.service;

import com.gamification.collectr.entity.Quest;
import com.gamification.collectr.exception.QuestNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface QuestService {

    Quest findById(Long id) throws QuestNotFoundException;

    Quest findQuestByQuestName(String name);

    List<Quest> findAll();

    void deleteById(long id);

    Quest saveQuest(Quest quest);

    void updateQuest(Quest quest);

    List<Quest> searchQuests(String keyword);

}