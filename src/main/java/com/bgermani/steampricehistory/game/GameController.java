package com.bgermani.steampricehistory.game;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.bgermani.steampricehistory.cron.RefreshSchedule;

@RestController
public class GameController {
    private static GameRepository gameRepository;

    GameController(GameRepository gameRepository) {
        GameController.gameRepository = gameRepository;
    }

    @GetMapping("/gamelist")
    List<Game> all() {
        return gameRepository.findAll();
    }

    @GetMapping("/gamelist/update")
    String refreshAll() throws URISyntaxException, IOException, InterruptedException {
        new RefreshSchedule(gameRepository).refreshAllGames();
        return "Updating existing games list.";
    }

    @GetMapping("/game/{steamId}")
    List<Game> findOne(@PathVariable("steamId") String steamId) {
        try {
            List<Game> games = gameRepository.findByGameId(steamId);
            return games;
        } catch (NoSuchElementException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Game ID %s not found", steamId));
        }
    }

    @PostMapping("/game/{gameId}")
    String postOne(@PathVariable("gameId") String gameId) throws URISyntaxException, IOException, InterruptedException {
        Game game = GameService.getPriceDetails(gameId);
        gameRepository.save(game);

        return game.toString() + " has been added to the database.";
    }
}
