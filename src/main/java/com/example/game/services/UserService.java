package com.example.game.services;

import java.util.List;
import com.example.game.model.entities.Game;
import com.example.game.model.entities.User;
import com.example.game.web.resources.UserBestGameResource;
import com.example.game.web.resources.UserCreateResource;
import org.springframework.validation.BindingResult;

public interface UserService {

  User validateAndSafeUser(UserCreateResource userCreateResource, BindingResult bindingResult);

  User userLogin(UserCreateResource userCreateResource, BindingResult bindingResult);

  User getUserByUsername(String username);

  List<UserBestGameResource> getAllUserWithBestGame();

  List<String> getAllBindingsErrors(BindingResult bindingResult);

  User getCurrentUser();

  void deleteById(Long id);

  void authenticate(UserCreateResource userCreateResource);

  void setCurrentGame(Game currentGame, Long id);

}
