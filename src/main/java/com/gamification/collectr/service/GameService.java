package com.gamification.collectr.service;
import com.gamification.collectr.entity.Badge;
import com.gamification.collectr.entity.Game;
import com.gamification.collectr.exception.GameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface GameService {

    Game findById(Long id) throws GameNotFoundException;

    Game findGameByName(String name);

    List<Game> findAll();

    void deleteById(long id);

    Game saveGame(Game game);

    void updateGame(Game game);

    List<Game> searchGames(String keyword);
}
