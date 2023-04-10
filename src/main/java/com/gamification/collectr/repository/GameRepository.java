package com.gamification.collectr.repository;

import com.gamification.collectr.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GameRepository extends JpaRepository<Game, Long> {

    Game findByName(String name);

    Game findByNameIgnoreCase(String name);

    @Query(
            value = "SELECT * FROM game g WHERE g.name LIKE %:keyword% OR g.id LIKE %:keyword% OR g.type LIKE %:keyword%",
            nativeQuery = true)
    List<Game> searchGames(@Param("keyword") String keyword);
}
