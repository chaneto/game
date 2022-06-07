package com.example.game.services;

import java.util.List;
import com.example.game.model.entities.CowsAndBulls;
import com.example.game.model.entities.Game;
import com.example.game.web.resources.NumberResource;
import org.springframework.validation.BindingResult;

public interface GameService {
  List<CowsAndBulls> getGameHistory(Long id);

  List<Game> findAllByUserIdSize();

  Game createGame();

  Game finishGame();

  boolean isAllDigitsIsDifferent(Character[] currentNumber);

  boolean isAllSymbolsIsDigit(Character[] currentNumber);

  List<CowsAndBulls> compare(NumberResource currentNumber, BindingResult bindingResult);

  String getFourDigitsNumber();

  Game continueGame(Long id);

  List<Game> findAllByUserId(Integer pageNo, Integer pageSize);
}
