package com.bgermani.steampricehistory;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import com.bgermani.steampricehistory.game.Game;
import com.bgermani.steampricehistory.game.GameService;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestGameService {

   @Autowired
   private GameService gameService;

   @Test
   public void itReturnsOk() throws URISyntaxException, IOException, InterruptedException {

      HttpClient client = HttpClient.newHttpClient();
      HttpRequest request = HttpRequest.newBuilder()
            .uri(new URI("http://localhost:8080/gamelist"))
            .GET()
            .build();

      HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

      assertEquals(HttpStatus.OK.value(), response.statusCode());
   }

   @Test
   public void itCreatesRecords() {
      List<Game> games = gameService.list();

      assertEquals(games.size(), 1);
   }
}