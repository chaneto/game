package com.example.game.services.impl;

import java.time.LocalDateTime;
import java.util.*;
import com.example.game.exceptions.ValidationException;
import com.example.game.model.entities.CowsAndBulls;
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
  private final UserService userService;
  private final CowsAndBullsRepository cowsAndBullsRepository;

  public GameServiceImpl(GameRepository gameRepository, UserService userService,
    CowsAndBullsRepository cowsAndBullsRepository) {
    this.gameRepository = gameRepository;
    this.userService = userService;
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
    game.setUser(this.userService.getUserByUsername(this.userService.getCurrentUser().getUsername()));
    game.setServerNumber(getFourDigitsNumber());
    Game saveGame = this.gameRepository.save(game);
    this.userService.setCurrentGame(saveGame, this.userService.getCurrentUser().getId());
    return saveGame;
  }

  public Game finishGame(){
    Game game = this.gameRepository.findById(this.userService.getCurrentUser().getCurrentGame().getId()).orElse(null);
    game.setCompleted(true);
    game.setEndDate(LocalDateTime.now());
    game.setNumberOfAttempts(Long.valueOf(game.getGameHistory().size()));
    this.userService.setCurrentGame(null, this.userService.getCurrentUser().getId());
    return this.gameRepository.save(game);
  }

  public Game continueGame(Long id){
   Game game = this.gameRepository.findById(id).orElse(null);
    this.userService.setCurrentGame(game, this.userService.getCurrentUser().getId());
   return game;
  }

  @Override
  public List<Game> findAllByUserId() {
    return this.gameRepository.findAllByUserIdOrderByIdDesc(this.userService.getCurrentUser().getId());
  }

  public List<CowsAndBulls> compare(NumberResource currentNumber, BindingResult bindingResult){
    if(bindingResult.hasErrors()){
      throw new ValidationException(this.userService.getAllBindingsErrors(bindingResult).toString());
    }
     int cows = 0;
     int bulls = 0;
    for (int i = 0; i < 4; i++) {
      if(currentNumber.getNumber().substring(i, i + 1).equals(this.userService.getCurrentUser().getCurrentGame().getServerNumber().substring(i, i + 1))){
          bulls++;
      }
      for (int j = 0; j < 4; j++) {
        if(currentNumber.getNumber().substring(j, j + 1).equals(this.userService.getCurrentUser().getCurrentGame().getServerNumber().substring(i, i + 1)) && i != j){
          cows++;
        }
      }

    }
    CowsAndBulls cowsAndBulls = new CowsAndBulls(currentNumber.getNumber(), cows, bulls,this.gameRepository.findById(this.userService.getCurrentUser().getCurrentGame().getId()).orElse(null));
    this.cowsAndBullsRepository.save(cowsAndBulls);
    Long id = this.userService.getCurrentUser().getCurrentGame().getId();
    if(bulls == 4){
      finishGame();
    }
    Game game = this.gameRepository.findById(id).orElse(null);
    game.setNumberOfAttempts(Long.valueOf(game.getGameHistory().size()));
    this.gameRepository.save(game);
    return game.getGameHistory();
  }


  public String getFourDigitsNumber(){
    String result = "";
    List<Integer> numbers = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      numbers.add(i);
    }
    Collections.shuffle(numbers);
    for(int i = 0; i < 4; i++){
      result += numbers.get(i);
    }
    return result;
  }
}
