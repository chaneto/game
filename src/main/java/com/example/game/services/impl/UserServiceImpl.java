package com.example.game.services.impl;

import java.util.ArrayList;
import java.util.List;
import com.example.game.exceptions.LoginException;
import com.example.game.exceptions.UsernameException;
import com.example.game.exceptions.NotFoundException;
import com.example.game.exceptions.ValidationException;
import com.example.game.model.entities.CurrentUser;
import com.example.game.model.entities.Game;
import com.example.game.model.entities.User;
import com.example.game.repositories.UserRepository;
import com.example.game.services.UserService;
import com.example.game.web.resources.UserBestGameResource;
import com.example.game.web.resources.UserCreateResource;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final CurrentUser currentUser;

  public UserServiceImpl(UserRepository userRepository,
    CurrentUser currentUser) {
    this.userRepository = userRepository;
    this.currentUser = currentUser;
  }

  @Override
  public User validateAndSafeUser(UserCreateResource userCreateResource,
    BindingResult bindingResult) {
    User user = new User();
    if (bindingResult.hasErrors()) {
      throw new ValidationException(getAllBindingsErrors(bindingResult).toString());
    }else if(!userCreateResource.getPassword().equals(userCreateResource.getConfirmPassword())) {
      throw new ValidationException("Password dont match!!!");
    }else if (this.userRepository.findByUsername(userCreateResource.getUsername()) != null) {
      throw new UsernameException("This username is already exists!!!");
    } else {
      user.setUsername(userCreateResource.getUsername());
      user.setPassword(userCreateResource.getPassword());
      return this.userRepository.save(user);
    }
  }

  @Override
  public User userLogin(UserCreateResource userCreateResource, BindingResult bindingResult) {
    User user = this.userRepository.findByUsername(userCreateResource.getUsername());
    if (bindingResult.hasErrors()) {
      throw new ValidationException(getAllBindingsErrors(bindingResult).toString());
    } else if (user == null) {
      throw new NotFoundException(
        "User with username: " + userCreateResource.getUsername() + " was not found!!!");
    } else if (user != null) {
      if (!user.getPassword().equals(userCreateResource.getPassword())) {
        throw new LoginException("Wrong user or password!!!");
      }
    }

    this.currentUser.setId(user.getId());
    this.currentUser.setUsername(user.getUsername());
    return user;
  }

  @Override
  public List<Game> getAllUserGames() {
    User user = this.userRepository.findByUsername(this.currentUser.getUsername());
    return user.getGames();
  }

  @Override
  public List<String> getAllBindingsErrors(BindingResult bindingResult) {
    List<String> messages = new ArrayList<>();
    if (bindingResult.hasErrors()) {
      for (int i = 0; i < bindingResult.getAllErrors().size(); i++) {
        messages.add(bindingResult.getAllErrors().get(i).getDefaultMessage());
      }
    }
    return messages;
  }

  @Override public void logout() {
    this.currentUser.setId(null);
    this.currentUser.setUsername(null);
  }

  @Override
  public User getUserByUsername(String username) {
    return this.userRepository.findByUsername(username);
  }

  @Override
  public List<UserBestGameResource> getAllUserWithBestGame(){
    List<UserBestGameResource> userBestGameResources = new ArrayList<>();
    List<String[]> users= this.userRepository.getAllUsersByGames();
    for (int i = 0; i < users.size(); i++) {
      UserBestGameResource userBestGameResource = new UserBestGameResource();
      userBestGameResource.setUsername(users.get(i)[0]);
      userBestGameResource.setNumberOfCompletedGames(users.get(i)[1]);
      userBestGameResource.setBestNumberOfAttempts(users.get(i)[2]);
      if(users.get(i)[3] != null){
      userBestGameResource.setBestTime(users.get(i)[3].substring(0, 8));
      }
      userBestGameResources.add(userBestGameResource);
    }
    return userBestGameResources;
  }
}
