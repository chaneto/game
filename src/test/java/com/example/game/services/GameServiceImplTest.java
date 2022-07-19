package com.example.game.services;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.time.LocalDateTime;
import java.util.List;
import com.example.game.exceptions.UnauthorizedException;
import com.example.game.exceptions.ValidationException;
import com.example.game.model.entities.CowsAndBulls;
import com.example.game.model.entities.Game;
import com.example.game.model.entities.User;
import com.example.game.repositories.GameRepository;
import com.example.game.repositories.UserRepository;
import com.example.game.web.resources.NumberResource;
import com.example.game.web.resources.UserCreateResource;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.BindingResult;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class GameServiceImplTest {

  @Autowired
  private GameService gameService;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private GameRepository gameRepository;

  @Autowired
  private UserService userService;

  UserCreateResource userCreateResource;
  Game game;
  BindingResult bindingResultTrue;
  BindingResult bindingResultFalse;

  @Before
  public void setup() {
    for (int i = 0; i < this.userRepository.count(); i++) {
     User user = this.userRepository.findAll().get(i);
     this.userRepository.deleteById(user.getId());
    }
    this.gameRepository.deleteAll();

    bindingResultTrue = mock(BindingResult.class);
    when(bindingResultTrue.hasErrors()).thenReturn(true);

    bindingResultFalse = mock(BindingResult.class);
    when(bindingResultFalse.hasErrors()).thenReturn(false);

    game = new Game();
    game.setStartDate(LocalDateTime.now());
    game.setNumberOfAttempts(0L);
    game.setCompleted(false);
    game.setEndDate(null);
    game.setServerNumber("1234");
    this.gameRepository.save(game);

    userCreateResource = new UserCreateResource();
    userCreateResource.setUsername("Ivan");
    userCreateResource.setPassword("12345");
    userCreateResource.setConfirmPassword("12345");
  }

  @Test
  public void testGetGameHistory() {
    Assert.assertEquals(0,
      this.gameService.getGameHistory(this.gameRepository.findAll().get(0).getId()).size());
  }

  @Test
  public void testCreateGame() {
    this.userService.validateAndSafeUser(userCreateResource, bindingResultFalse);
    this.userService.userLogin(userCreateResource, bindingResultFalse);
    Game game = this.gameService.createGame();
    Assert.assertNotNull(game);
    Assert.assertNull(game.getEndDate());
  }

  @Test
  public void testFinishGame() {
    this.userService.validateAndSafeUser(userCreateResource, bindingResultFalse);
    this.userService.userLogin(userCreateResource, bindingResultFalse);
    Game game = this.gameService.createGame();
    Assert.assertNotNull(game);
    Assert.assertNull(game.getEndDate());
    game = this.gameService.finishGame(game);
    Assert.assertNotNull(game.getEndDate());
  }

  @Test
  public void testContinueGame() {
    this.userService.validateAndSafeUser(userCreateResource, bindingResultFalse);
    this.userService.userLogin(userCreateResource, bindingResultFalse);
    Game game = this.gameService.createGame();
    Assert.assertNotNull(game);
    Game gameContinue = this.gameService.continueGame(game.getId());
    Assert.assertEquals(gameContinue.getId(), game.getId());
  }

  @Test(expected = UnauthorizedException.class)
  public void testContinueGameWithInvalidId() {
    this.userService.validateAndSafeUser(userCreateResource, bindingResultFalse);
    User user = this.userService.userLogin(userCreateResource, bindingResultFalse);
    Game game = this.gameService.createGame();
    UserCreateResource user1 = new UserCreateResource();
    user1.setUsername("petko");
    user1.setPassword("12345");
    user1.setConfirmPassword("12345");
    this.userService.validateAndSafeUser(user1, bindingResultFalse);
    this.userService.userLogin(user1, bindingResultFalse);
    Long cur = this.userService.getCurrentUser().getId();
    Long gameu = game.getUser().getId();
    this.gameService.continueGame(game.getId());
  }

  @Test
  public void testFindAllByUserId() {
    this.userService.validateAndSafeUser(userCreateResource, bindingResultFalse);
    User user = this.userService.userLogin(userCreateResource, bindingResultFalse);
    Game game = this.gameService.createGame();
    Assert.assertEquals(1, this.gameService.findAllByUserId(0, 4).size());
  }

  @Test
  public void testFindAllByUserIdWithOutGames() {
    this.userService.validateAndSafeUser(userCreateResource, bindingResultFalse);
    User user = this.userService.userLogin(userCreateResource, bindingResultFalse);
    Assert.assertEquals(0, this.gameService.findAllByUserId(0, 4).size());
  }

  @Test
  public void testIsAllSymbolsIsDigitWithInvalidNumber() {
    Character[] number = new Character[] {1, 2, 3, 'r'};
    Assert.assertFalse(this.gameService.isAllSymbolsIsDigit(number));
  }

  @Test
  public void testIsAllDigitsIsDifferent() {
    Character[] number = new Character[] {1, 2, 3, 3};
    Assert.assertFalse(this.gameService.isAllDigitsIsDifferent(number));
  }

  @Test(expected = ValidationException.class)
  public void testCompareWithBindingException() {
    this.userService.validateAndSafeUser(userCreateResource, bindingResultFalse);
    User user = this.userService.userLogin(userCreateResource, bindingResultFalse);
    Game game = this.gameService.createGame();
    NumberResource numberResource = new NumberResource();
    numberResource.setNumber("123");
    this.gameService.compare(numberResource, bindingResultTrue, this.gameService.getCurrentGameId());
  }

  @Test(expected = ValidationException.class)
  public void testCompareWithNotDifferentDigits() {
    this.userService.validateAndSafeUser(userCreateResource, bindingResultFalse);
    User user = this.userService.userLogin(userCreateResource, bindingResultFalse);
    Game game = this.gameService.createGame();
    NumberResource numberResource = new NumberResource();
    numberResource.setNumber("1233");
    this.gameService.compare(numberResource, bindingResultFalse, this.gameService.getCurrentGameId());
  }

  @Test(expected = ValidationException.class)
  public void testCompareWithNotDigitsInNUmber() {
    this.userService.validateAndSafeUser(userCreateResource, bindingResultFalse);
    User user = this.userService.userLogin(userCreateResource, bindingResultFalse);
    Game game = this.gameService.createGame();
    NumberResource numberResource = new NumberResource();
    numberResource.setNumber("123r");
    this.gameService.compare(numberResource, bindingResultFalse, this.gameService.getCurrentGameId());
  }


  @Test
  public void testCompare() {
    this.userService.validateAndSafeUser(userCreateResource, bindingResultFalse);
    User user = this.userService.userLogin(userCreateResource, bindingResultFalse);
    Game game = this.gameService.createGame();
    NumberResource numberResource = new NumberResource();
    numberResource.setNumber("1234");
    List<CowsAndBulls> cowsAndBulls = this.gameService.compare(numberResource, bindingResultFalse, this.gameService.getCurrentGameId());
    Assert.assertEquals(1, cowsAndBulls.size());
  }

}
