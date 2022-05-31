package com.example.game.web;

import java.util.List;
import javax.validation.Valid;
import com.example.game.model.entities.Game;
import com.example.game.model.entities.User;
import com.example.game.services.UserService;
import com.example.game.web.assembler.GameAssembler;
import com.example.game.web.assembler.UserAssembler;
import com.example.game.web.resources.UserCreateResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

  private final UserService userService;
  private final UserAssembler userAssembler;
  private final GameAssembler gameAssembler;

  public UserController(UserService userService,
    UserAssembler userAssembler, GameAssembler gameAssembler) {
    this.userService = userService;
    this.userAssembler = userAssembler;
    this.gameAssembler = gameAssembler;
  }

  @PostMapping("/register")
  public ResponseEntity<?> register(@RequestBody @Valid UserCreateResource userCreateResource, BindingResult bindingResult) {
    User user = this.userService.validateAndSafeUser(userCreateResource, bindingResult);
    return new ResponseEntity<>(this.userAssembler.assembleUserResource(user), HttpStatus.CREATED);
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody @Valid UserCreateResource userCreateResource, BindingResult bindingResult){
    User user = this.userService.userLogin(userCreateResource, bindingResult);
    return new ResponseEntity<>(this.userAssembler.assembleUserResource(user), HttpStatus.OK);
  }

  @PostMapping("/logout")
  public void logout(){
    this.userService.logout();
  }

  @GetMapping("/games")
  public ResponseEntity<?> getAllUserGame(){
    List<Game> games = this.userService.getAllUserGames();
    return new ResponseEntity<>(this.gameAssembler.assembleGamesResource(games), HttpStatus.OK);
  }

  @GetMapping("/home")
  public ResponseEntity<?> getAllUserWithBestGame(){
     return new ResponseEntity<>(this.userService.getAllUserWithBestGame(), HttpStatus.OK);
  }
}
