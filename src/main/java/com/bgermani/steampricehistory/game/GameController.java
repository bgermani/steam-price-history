package com.bgermani.steampricehistory.game;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

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

    @GetMapping("/game/{steamId}")
    Game findOne(@PathVariable("steamId") String steamId) {
        try {
            Optional<Game> opt = gameRepository.findById(Long.parseLong(steamId));
            return opt.get();
        } catch (NoSuchElementException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Game ID %s not found", steamId));
        }
    }

    @PostMapping("/game/{gameId}")
    String postOne(@PathVariable("gameId") String gameId) throws URISyntaxException, IOException, InterruptedException {
        Game game = getPriceDetails(gameId);
        gameRepository.save(game);

        return game.toString() + " has been added to the database.";
    }

    public static Game getPriceDetails(String gameId) throws URISyntaxException, IOException, InterruptedException {

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(String.format("https://store.steampowered.com/api/appdetails?appids=%s", gameId)))
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

            JsonObject object = JsonParser.parseString(response.body().toString()).getAsJsonObject();
            String name = object.getAsJsonObject(gameId).getAsJsonObject("data").getAsJsonPrimitive("name")
                    .getAsString();
            String price = object.getAsJsonObject(gameId).getAsJsonObject("data").getAsJsonObject("price_overview")
                    .getAsJsonPrimitive("final_formatted").getAsString();

            Game newGame = new Game();
            newGame.setGameId(Long.parseLong(gameId));
            newGame.setName(name);
            newGame.setPrice(price);
            newGame.setDate(new Date());

            return newGame;
    }
}
