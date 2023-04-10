package com.gamification.collectr.service.impl;

import com.gamification.collectr.entity.Quest;
import com.gamification.collectr.exception.QuestNotFoundException;
import com.gamification.collectr.repository.QuestRepository;
import com.gamification.collectr.service.QuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class QuestServiceImpl implements QuestService {

    @Autowired
    private final QuestRepository questRepository;

    @Autowired
    public QuestServiceImpl(QuestRepository questRepository) {
        this.questRepository = questRepository;
    }
    @Override
    public Quest findById(Long id) throws QuestNotFoundException {
        return questRepository.findById(id).orElseThrow(QuestNotFoundException::new);
    }

    @Override
    public Quest findQuestByQuestName(String name) {
        return questRepository.findByQuestNameIgnoreCase(name);
    }

    @Override
    public List<Quest> findAll() {
        return questRepository.findAll();
    }

    @Override
    public void deleteById(long id) {
        questRepository.deleteById(id);
    }

    @Override
    public Quest saveQuest(Quest receivedQuest) {

        Quest quest = new Quest(receivedQuest);
        return questRepository.save(quest);
    }

    @Override
    public void updateQuest(Quest quest) {
        questRepository.save(quest);
    }

    @Override
    public List<Quest> searchQuests(String keyword) {
        return questRepository.searchQuests(keyword);
    }
}
