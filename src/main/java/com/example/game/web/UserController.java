package com.example.game.web;

import java.util.List;
import javax.validation.Valid;
import com.example.game.model.entities.User;
import com.example.game.services.UserService;
import com.example.game.web.assembler.GameAssembler;
import com.example.game.web.assembler.UserAssembler;
import com.example.game.web.resources.UserBestGameResource;
import com.example.game.web.resources.UserCreateResource;
import com.example.game.web.resources.UserResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/users")
public class UserController {

  private final UserService userService;
  private final UserAssembler userAssembler;
  private final GameAssembler gameAssembler;

  @Autowired
  public UserController(UserService userService, UserAssembler userAssembler,
    GameAssembler gameAssembler) {
    this.userService = userService;
    this.userAssembler = userAssembler;
    this.gameAssembler = gameAssembler;
  }

  @ApiOperation(httpMethod = "POST", value = "Register User.", response = UserResource.class)
  @PostMapping("/register")
  public ResponseEntity<UserResource> register(
    @RequestBody @Valid UserCreateResource userCreateResource, BindingResult bindingResult) {
    User user = this.userService.validateAndSafeUser(userCreateResource, bindingResult);
    return new ResponseEntity<>(this.userAssembler.assembleUserResource(user), HttpStatus.CREATED);
  }

  @ApiOperation(httpMethod = "POST", value = "Login User.", response = UserResource.class)
  @PostMapping("/login")
  public ResponseEntity<UserResource> login(
    @RequestBody @Valid UserCreateResource userCreateResource, BindingResult bindingResult) {
    User user = this.userService.userLogin(userCreateResource, bindingResult);
    return new ResponseEntity<>(this.userAssembler.assembleUserResource(user), HttpStatus.OK);
  }

  @ApiOperation(httpMethod = "GET", value = "All registered users.", response = UserBestGameResource.class)
  @GetMapping("/all")
  public ResponseEntity<List<UserBestGameResource>> getAllUserWithBestGame() {
    return new ResponseEntity<>(this.userService.getAllUserWithBestGame(), HttpStatus.OK);
  }


  ppppppppppppppp
}
