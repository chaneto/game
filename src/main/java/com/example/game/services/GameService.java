package com.example.game.services;

import java.util.List;
import com.example.game.model.entities.CowsAndBulls;
import com.example.game.model.entities.Game;
import com.example.game.web.resources.NumberResource;
import org.springframework.validation.BindingResult;

public interface GameService {
  List<CowsAndBulls> getGameHistory(Long id);

  Game createGame();

  List<CowsAndBulls> compare(NumberResource currentNumber, BindingResult bindingResult);

  Integer[] getFourDigitsNumber();
}
