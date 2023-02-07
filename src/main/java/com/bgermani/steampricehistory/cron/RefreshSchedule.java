package com.bgermani.steampricehistory.cron;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.bgermani.steampricehistory.game.Game;
import com.bgermani.steampricehistory.game.GameController;
import com.bgermani.steampricehistory.game.GameRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class RefreshSchedule {
    private final GameRepository gameRepository;

    RefreshSchedule(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Scheduled(cron = "0 0 20 * * *")
    public void refreshAllGames() throws URISyntaxException, IOException, InterruptedException {

        List<Long> allGameIds = gameRepository.findAll().stream().map(Game::getGameId).collect(Collectors.toList());
        List<Long> usedGameIds = new ArrayList<Long>();

        log.info("Starting price refresh.");
        for (Long gameId : allGameIds) {
            if (usedGameIds.contains(gameId)) {
                log.info("Game ID " + String.valueOf(gameId) + " already checked today, skipping.");
                continue;
            }

            log.info("Saving game ID " + String.valueOf(gameId));
            usedGameIds.add(gameId);

            Game game = GameController.getPriceDetails(String.valueOf(gameId));
            game.setDate(new Date());
            gameRepository.save(game);
        }
        log.info("Finished price refresh.");
    }
}
