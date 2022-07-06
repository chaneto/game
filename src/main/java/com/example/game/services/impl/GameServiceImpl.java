package com.example.game.services.impl;

import java.time.LocalDateTime;
import java.util.*;
import com.example.game.exceptions.NullPointerException;
import com.example.game.exceptions.UnauthorizedException;
import com.example.game.exceptions.ValidationException;
import com.example.game.model.entities.CowsAndBulls;
import com.example.game.model.entities.Game;
import com.example.game.repositories.CowsAndBullsRepository;
import com.example.game.repositories.GameRepository;
import com.example.game.services.GameService;
import com.example.game.services.UserService;
import com.example.game.web.resources.NumberResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

@Service
public class GameServiceImpl implements GameService {

  private final List<Integer> numbers = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
  private final GameRepository gameRepository;
  private final UserService userService;
  private final CowsAndBullsRepository cowsAndBullsRepository;

  @Autowired
  public GameServiceImpl(GameRepository gameRepository, UserService userService,
    CowsAndBullsRepository cowsAndBullsRepository) {
    this.gameRepository = gameRepository;
    this.userService = userService;
    this.cowsAndBullsRepository = cowsAndBullsRepository;
  }

  @Override
  @Cacheable(value = "history", key = "#id")
  public List<CowsAndBulls> getGameHistory(Long id) {
    Game game = this.gameRepository.findById(id)
      .orElseThrow(() -> new NullPointerException("This game cannot be found!!!"));
    return game.getGameHistory();
  }

  @Override
  @CacheEvict(value = "games", allEntries = true)
  public Game createGame() {
    Game game = new Game();
    game.setNumberOfAttempts(0L);
    game.setCompleted(false);
    game.setStartDate(LocalDateTime.now());
    game.setUser(
      this.userService.getUserByUsername(this.userService.getCurrentUser().getUsername()));
    game.setServerNumber(getFourDigitsNumber());
    Game saveGame = saveGame(game);
    this.userService.setCurrentGame(saveGame, this.userService.getCurrentUser().getId());
    return saveGame;
  }

  public Game saveGame(Game game) {
    return this.gameRepository.save(game);
  }

  @Override
  public Game finishGame() {
    Game game =
      this.gameRepository.findById(this.userService.getCurrentUser().getCurrentGame().getId())
        .orElseThrow(() -> new NullPointerException("This game cannot be found!!!"));
    game.setCompleted(true);
    game.setEndDate(LocalDateTime.now());
    long numberAttempts = game.getGameHistory().size();
    game.setNumberOfAttempts(numberAttempts);
    this.userService.setCurrentGameToNull();
    return saveGame(game);
  }

  @Override
  @Cacheable(value = "continue", key = "#id")
  public Game continueGame(Long id) {
    Game game = this.gameRepository.findById(id)
      .orElseThrow(() -> new NullPointerException("This game cannot be found!!!"));
    if (!game.getUser().getId().equals(this.userService.getCurrentUser().getId())) {
      throw new UnauthorizedException("You are not the owner of the game!!!");
    }
    this.userService.setCurrentGame(game, this.userService.getCurrentUser().getId());
    return game;
  }

  @Override
  @Cacheable(value = "games", key = "#pageNo")
  public List<Game> findAllByUserId(Integer pageNo, Integer pageSize) {
    if (pageNo < 0) {
      throw new ValidationException("Page index must not be less than zero!!!");
    }
    Sort sort = Sort.by("id").descending();
    Pageable paging = PageRequest.of(pageNo, pageSize, sort);
    Page<Game> pagedResult =
      this.gameRepository.findAllByUserId(this.userService.getCurrentUser().getId(), paging);
    if (pagedResult.hasContent()) {
      return pagedResult.getContent();
    } else {
      return Collections.emptyList();
    }
  }

  @Override
  @Cacheable(value = "games")
  public List<Game> findAllCurrentUserGames() {
    return this.gameRepository.findAllByUserId(this.userService.getCurrentUser().getId());
  }

  public void createAndSaveCowsAndBulls(String currentNumber,int cows,int bulls, Game currentGame) {
    CowsAndBulls cowsAndBulls = new CowsAndBulls(currentNumber, cows, bulls, currentGame);
    this.cowsAndBullsRepository.save(cowsAndBulls);
  }

  @Override
  public Long getCurrentGameId(){
  return this.userService.getCurrentUser().getCurrentGame().getId();
  }

  @Override
  @Caching(evict = { @CacheEvict(value = {"games", "continue"}, allEntries = true)},
    put = { @CachePut(value = {"history"}, key = "#currentGameId")})
  public List<CowsAndBulls> compare(NumberResource currentNumber, BindingResult bindingResult, Long currentGameId) {
    if (bindingResult.hasErrors()) {
      throw new ValidationException(
        this.userService.getAllBindingsErrors(bindingResult).toString());
    }

    Character[] currentNum = new Character[4];
    Character[] serverNum = new Character[4];

    for (int i = 0; i < 4; i++) {
      currentNum[i] = currentNumber.getNumber().charAt(i);
      serverNum[i] = this.userService.getCurrentUser().getCurrentGame().getServerNumber().charAt(i);
    }

    if (!isAllDigitsIsDifferent(currentNum)) {
      throw new ValidationException("The digits must be different!!!");
    }

    if (!isAllSymbolsIsDigit(currentNum)) {
      throw new ValidationException("Must all symbols is digit!!!");
    }

    Integer[] cowsAndBullsCompare = getCowsAndBulls(currentNum, serverNum, getCurrentGameId());
    int cows = cowsAndBullsCompare[0];
    int bulls = cowsAndBullsCompare[1];
    createAndSaveCowsAndBulls(currentNumber.getNumber(), cows, bulls, this.gameRepository.findById(currentGameId).orElseThrow(() -> new NullPointerException("This game cannot be found!!!")));
    if (bulls == 4) {
      finishGame();
    }
    Game game = this.gameRepository.findById(currentGameId).orElseThrow(() -> new NullPointerException("This game cannot be found!!!"));;
    long numberAttempts = game.getGameHistory().size();
    game.setNumberOfAttempts(numberAttempts);
    this.gameRepository.save(game);
    return game.getGameHistory();
  }

  public Integer[] getCowsAndBulls( Character[] currentNum, Character[] serverNum, Long id){
    int cows = 0;
    int bulls = 0;
    for (int i = 0; i < 4; i++) {
      if (currentNum[i] == serverNum[i]) {
        bulls++;
      }
      for (int j = 0; j < 4; j++) {
        if (currentNum[j] == serverNum[i] && i != j) {
          cows++;
        }
      }
    }
    return new Integer[]{cows, bulls};
  }

  public boolean isAllDigitsIsDifferent(Character[] currentNumber) {
    boolean allDigitsIsDifferent = true;

    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 4; j++) {
        if (i == j) {
          continue;
        }
        if (currentNumber[i] == currentNumber[j]) {
          allDigitsIsDifferent = false;
          break;
        }
      }
    }
    return allDigitsIsDifferent;
  }

  public boolean isAllSymbolsIsDigit(Character[] currentNumber) {
    boolean isAllSymbolsIsDigit = true;
    for (int j = 0; j < 4; j++) {
      if (!Character.isDigit(currentNumber[j])) {
        isAllSymbolsIsDigit = false;
        break;
      }

    }
    return isAllSymbolsIsDigit;
  }


  public String getFourDigitsNumber() {
    String result = "";
    Collections.shuffle(numbers);
    for (int i = 0; i < 4; i++) {
      result += numbers.get(i);
    }
    return result;
  }
}
