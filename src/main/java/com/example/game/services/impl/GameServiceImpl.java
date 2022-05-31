package com.example.game.services.impl;

import java.time.LocalDateTime;
import java.util.*;

import com.example.game.exceptions.ValidationException;
import com.example.game.model.entities.CowsAndBulls;
import com.example.game.model.entities.CurrentGame;
import com.example.game.model.entities.CurrentUser;
import com.example.game.model.entities.Game;
import com.example.game.repositories.CowsAndBullsRepository;
import com.example.game.repositories.GameRepository;
import com.example.game.services.GameService;
import com.example.game.services.UserService;
import com.example.game.web.resources.NumberResource;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

@Service
public class GameServiceImpl implements GameService {

  private final GameRepository gameRepository;
  private final CurrentUser currentUser;
  private final UserService userService;
  private final CurrentGame currentGame;
  private final CowsAndBullsRepository cowsAndBullsRepository;

  public GameServiceImpl(GameRepository gameRepository,
    CurrentUser currentUser, UserService userService,
    CurrentGame currentGame,
    CowsAndBullsRepository cowsAndBullsRepository) {
    this.gameRepository = gameRepository;
    this.currentUser = currentUser;
    this.userService = userService;
    this.currentGame = currentGame;
    this.cowsAndBullsRepository = cowsAndBullsRepository;
  }

  public List<CowsAndBulls> getGameHistory(Long id){
     Game game = this.gameRepository.findById(id).orElse(null);
     return game.getGameHistory();
  }

  public Game createGame(){
    Game game = new Game();
    game.setNumberOfAttempts(0L);
    game.setCompleted(false);
    game.setStartDate(LocalDateTime.now());
    game.setUser(this.userService.getUserByUsername(this.currentUser.getUsername()));
    game.setServerNumber(getFourDigitsNumber());
    Game saveGame = this.gameRepository.save(game);
    this.currentGame.setServerNumber(saveGame.getServerNumber());
    this.currentGame.setId(saveGame.getId());
    return saveGame;
  }

  public Game finishGame(){
    Game game = this.gameRepository.findById(this.currentGame.getId()).orElse(null);
    game.setCompleted(true);
    game.setEndDate(LocalDateTime.now());
    game.setNumberOfAttempts(Long.valueOf(game.getGameHistory().size()));
    this.currentGame.setId(null);
    this.currentGame.setServerNumber(null);
    return this.gameRepository.save(game);
  }

  public List<CowsAndBulls> compare(NumberResource currentNumber, BindingResult bindingResult){
    if(bindingResult.hasErrors()){
      throw new ValidationException(this.userService.getAllBindingsErrors(bindingResult).toString());
    }
     Integer[] number = new Integer[4];
     int cows = 0;
     int bulls = 0;
    for (int i = 0; i < currentNumber.getNumber().length(); i++) {
      number[i] = Integer.parseInt(currentNumber.getNumber().substring(i, i + 1));
    }
    for (int i = 0; i < 4; i++) {
      if(number[i] == this.currentGame.getServerNumber()[i]){
          bulls++;
      }
      for (int j = 0; j < 4; j++) {
        if(number[j] == this.currentGame.getServerNumber()[i] && i != j){
          cows++;
        }
      }

    }
    String numberToString = Arrays.toString(number).replaceAll("\\[|\\]|,|\\s", "");
    CowsAndBulls cowsAndBulls = new CowsAndBulls(numberToString, cows, bulls,this.gameRepository.findById(this.currentGame.getId()).orElse(null));
    this.cowsAndBullsRepository.save(cowsAndBulls);
    Long id = this.currentGame.getId();
    if(bulls == 4){
      finishGame();
    }
    Game game = this.gameRepository.findById(id).orElse(null);
    List<CowsAndBulls> cowsAndBulls1 = game.getGameHistory();
    return cowsAndBulls1;
  }

  public Integer[] getFourDigitsNumber(){
    Integer[] result = new Integer[4];
    List<Integer> numbers = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      numbers.add(i);
    }
    Collections.shuffle(numbers);
    for(int i = 0; i < 4; i++){
      result[i] = numbers.get(i);
    }
    return result;
  }
}
