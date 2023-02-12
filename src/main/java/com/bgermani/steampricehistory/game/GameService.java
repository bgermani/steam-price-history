package com.bgermani.steampricehistory.game;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class GameService {
    @Autowired
    private GameRepository gameRepository;

    @PersistenceContext
    EntityManager entityManager;

    public List<Game> list() {
        return gameRepository.findAll();
    }

    public List<?> findByGameId(String gameId) {

        List<?> games = entityManager.createQuery("SELECT game from Game game where game.gameId = ?1")
                .setParameter(1, Long.parseLong(gameId))
                .getResultList();
        return games;
    }
}
