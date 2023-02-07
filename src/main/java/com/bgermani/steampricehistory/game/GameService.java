package com.bgermani.steampricehistory.game;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameService {
    @Autowired
    private GameRepository gameRepository;

    public List<Game> list() {
        return gameRepository.findAll();
    }
}
