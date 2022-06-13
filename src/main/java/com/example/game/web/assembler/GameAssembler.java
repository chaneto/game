package com.example.game.web.assembler;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import com.example.game.model.entities.CowsAndBulls;
import com.example.game.model.entities.Game;
import com.example.game.web.resources.CowsAndBullsResource;
import com.example.game.web.resources.GameResource;
import org.springframework.stereotype.Component;

@Component
public class GameAssembler {

  public GameResource assembleGameResource(Game game) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    GameResource gameResource = new GameResource();
    gameResource.setId(game.getId());
    gameResource.setServerNumber(game.getServerNumber());
    if (game.getStartDate() != null) {
      gameResource.setStartDate(game.getStartDate().format(formatter));
    }
    if (game.getEndDate() != null) {
      gameResource.setEndDate(game.getEndDate().format(formatter));
    }
    gameResource.setCompleted(game.isCompleted());
    gameResource.setNumberOfAttempts(game.getNumberOfAttempts());
    return gameResource;
  }

  public List<GameResource> assembleGamesResource(List<Game> games) {
    List<GameResource> gameResources = new ArrayList<>();
    for (Game game : games)
      gameResources.add(assembleGameResource(game));
    return gameResources;
  }

  public List<CowsAndBullsResource> assembleCowsAndBullsResource(List<CowsAndBulls> cowsAndBulls) {
    List<CowsAndBullsResource> cowsAndBullsResources = new ArrayList<>();
    for (CowsAndBulls item : cowsAndBulls) {
      CowsAndBullsResource cowsAndBullsResource = new CowsAndBullsResource();
      cowsAndBullsResource.setNumber(item.getNumber());
      cowsAndBullsResource.setBulls(item.getBulls());
      cowsAndBullsResource.setCows(item.getCows());
      cowsAndBullsResources.add(cowsAndBullsResource);
    }
    return cowsAndBullsResources;
  }
}
