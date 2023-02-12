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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

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

    public static Game getPriceDetails(String gameId) throws URISyntaxException, IOException, InterruptedException {
    
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(String.format("https://store.steampowered.com/api/appdetails?appids=%s", gameId)))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
    
        JsonObject game = JsonParser.parseString(response.body().toString()).getAsJsonObject().getAsJsonObject(gameId)
                .getAsJsonObject("data");
        String name = game.getAsJsonPrimitive("name").getAsString();
        String price = game.getAsJsonObject("price_overview").getAsJsonPrimitive("final_formatted").getAsString();
    
        Game newGame = new Game();
        newGame.setGameId(Long.parseLong(gameId));
        newGame.setName(name);
        newGame.setPrice(price);
        newGame.setDate(new Date());
    
        return newGame;
    }
}
