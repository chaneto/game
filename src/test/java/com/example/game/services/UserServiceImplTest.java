package com.example.game.services;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.time.LocalDateTime;
import com.example.game.exceptions.LoginException;
import com.example.game.exceptions.UsernameException;
import com.example.game.exceptions.ValidationException;
import com.example.game.model.entities.Game;
import com.example.game.model.entities.User;
import com.example.game.repositories.GameRepository;
import com.example.game.repositories.UserRepository;
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
public class UserServiceImplTest {

  @Autowired
  private UserService userService;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private GameRepository gameRepository;

  UserCreateResource userCreateResource;
  Game game;
  BindingResult bindingResultTrue;
  BindingResult bindingResultFalse;

  @Before
  public void setup() {
    this.userRepository.deleteAll();
    this.gameRepository.deleteAll();

    game = new Game();
    game.setStartDate(LocalDateTime.now());
    game.setNumberOfAttempts(0L);
    game.setCompleted(false);
    game.setEndDate(null);
    game.setServerNumber("1234");

    userCreateResource = new UserCreateResource();
    userCreateResource.setUsername("Ivan");
    userCreateResource.setPassword("12345");
    userCreateResource.setConfirmPassword("12345");

    bindingResultTrue = mock(BindingResult.class);
    when(bindingResultTrue.hasErrors()).thenReturn(true);

    bindingResultFalse = mock(BindingResult.class);
    when(bindingResultFalse.hasErrors()).thenReturn(false);
  }

  @Test
  public void testValidateAndSafeUser(){
    Assert.assertEquals(0, this.userRepository.count());
    User user = this.userService.validateAndSafeUser(userCreateResource, bindingResultFalse);
    Assert.assertEquals(1, this.userRepository.count());
  }

  @Test(expected = ValidationException.class)
  public void testValidateAndSafeUserWithWrongConfirmPassword(){
    userCreateResource.setConfirmPassword("1234567");
    User user = this.userService.validateAndSafeUser(userCreateResource, bindingResultFalse);
  }

  @Test(expected = UsernameException.class)
  public void testValidateAndSafeUserWithExistingUsername(){
    this.userService.validateAndSafeUser(userCreateResource, bindingResultFalse);
    User user = this.userService.validateAndSafeUser(userCreateResource, bindingResultFalse);
  }

  @Test(expected = ValidationException.class)
  public void testValidateAndSafeUserWithBindingErrors(){
    User user = this.userService.validateAndSafeUser(userCreateResource, bindingResultTrue);
  }

  @Test
  public void testUserLogin(){
    this.userService.validateAndSafeUser(userCreateResource, bindingResultFalse);
    User user = this.userService.userLogin(userCreateResource, bindingResultFalse);
  }

  @Test(expected = ValidationException.class)
  public void testUserLoginWithBindingErrors(){
    User user = this.userService.userLogin(userCreateResource, bindingResultTrue);
  }

  @Test(expected = ValidationException.class)
  public void testUserLoginWithUnExistUser(){
    User user = this.userService.userLogin(userCreateResource, bindingResultTrue);
  }

  @Test
  public void testGetCurrentUser(){
    this.userService.validateAndSafeUser(userCreateResource, bindingResultFalse);
    User user = this.userService.userLogin(userCreateResource, bindingResultFalse);
    User currentUser = this.userService.getCurrentUser();
    Assert.assertEquals(user.getUsername(), currentUser.getUsername());

  }

  @Test(expected = NullPointerException.class)
  public void testGetCurrentUserWithNoLogedUser(){
    this.userService.validateAndSafeUser(userCreateResource, bindingResultFalse);
    User currentUser = this.userService.getCurrentUser();
  }

  @Test
  public void testSetCurrentGame(){
    Game currentGame = this.gameRepository.save(game);
    this.userService.validateAndSafeUser(userCreateResource, bindingResultFalse);
    User user = this.userService.userLogin(userCreateResource, bindingResultFalse);
    Assert.assertEquals(null, user.getCurrentGame());
    this.userService.setCurrentGame(game, user.getId());
    Assert.assertEquals(this.userService.getCurrentUser().getCurrentGame().getId(), currentGame.getId());
  }

  @Test
  public void testGetUserByUsername(){
    this.userService.validateAndSafeUser(userCreateResource, bindingResultFalse);
    this.userService.userLogin(userCreateResource, bindingResultFalse);
    User user = this.userService.getUserByUsername("Ivan");
    Assert.assertEquals("Ivan", user.getUsername());
  }

  @Test(expected = LoginException.class)
  public  void testAuthenticate(){
    this.userService.validateAndSafeUser(userCreateResource, bindingResultFalse);
    userCreateResource.setPassword("123456789");
    this.userService.authenticate(userCreateResource);
  }
}
