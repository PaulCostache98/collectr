package com.gamification.collectr.service.impl;

import com.gamification.collectr.entity.Game;
import com.gamification.collectr.exception.GameNotFoundException;
import com.gamification.collectr.repository.GameRepository;
import com.gamification.collectr.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameServiceImpl implements GameService {

    @Autowired
    private final GameRepository gameRepository;

    @Autowired
    public GameServiceImpl(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }
    @Override
    public Game findById(Long id) throws GameNotFoundException, GameNotFoundException {
        return gameRepository.findById(id).orElseThrow(GameNotFoundException::new);
    }

    @Override
    public Game findGameByName(String name) {
        return gameRepository.findByNameIgnoreCase(name);
    }

    @Override
    public List<Game> findAll() {
        return gameRepository.findAll();
    }

    @Override
    public void deleteById(long id) {
        gameRepository.deleteById(id);
    }

    @Override
    public Game saveGame(Game receivedGame) {

        Game game = new Game(receivedGame);
        return gameRepository.save(game);
    }

    @Override
    public void updateGame(Game game) {
        gameRepository.save(game);
    }

    @Override
    public List<Game> searchGames(String keyword) {
        return gameRepository.searchGames(keyword);
    }
}
