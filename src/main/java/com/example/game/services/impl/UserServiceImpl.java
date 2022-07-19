package com.example.game.services.impl;

import java.util.ArrayList;
import java.util.List;
import com.example.game.exceptions.LoginException;
import com.example.game.exceptions.NullPointerException;
import com.example.game.exceptions.UsernameException;
import com.example.game.exceptions.ValidationException;
import com.example.game.model.entities.Game;
import com.example.game.model.entities.User;
import com.example.game.repositories.UserRepository;
import com.example.game.services.UserService;
import com.example.game.web.resources.UserBestGameResource;
import com.example.game.web.resources.UserCreateResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

@Service
public class UserServiceImpl implements UserService {

  private static final String TOPIC = "users-register";
  private static final String GROUP_ID = "register_group";
  private static final Logger logger = LoggerFactory.getLogger(User.class);

  private final KafkaTemplate<String, Object> kafkaTemplate;
  private final UserRepository userRepository;
  private final GameUserDetailService gameUserDetailService;
  private final PasswordEncoder passwordEncoder;

  @Autowired
  public UserServiceImpl(KafkaTemplate<String, Object> kafkaTemplate,
    UserRepository userRepository, GameUserDetailService gameUserDetailService,
    PasswordEncoder passwordEncoder) {
    this.kafkaTemplate = kafkaTemplate;
    this.userRepository = userRepository;
    this.gameUserDetailService = gameUserDetailService;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public User validateAndSafeUser(UserCreateResource userCreateResource,
    BindingResult bindingResult) {
    User user = new User();
    if (bindingResult.hasErrors()) {
      throw new ValidationException(getAllBindingsErrors(bindingResult).toString());
    } else if (!userCreateResource.getPassword().equals(userCreateResource.getConfirmPassword())) {
      throw new ValidationException("Password dont match!!!");
    } else if (this.userRepository.findByUsername(userCreateResource.getUsername()) != null) {
      throw new UsernameException("This username is already exists!!!");
    } else {
      user.setUsername(userCreateResource.getUsername());
      user.setPassword(this.passwordEncoder.encode(userCreateResource.getPassword()));
      this.kafkaTemplate.send(TOPIC, user);
      return user;
    }
  }

  @KafkaListener(topics = TOPIC, groupId = GROUP_ID)
  public void saveUser(User user) {
    this.userRepository.save(user);
    logger.info("REGISTERED NEW USER: " + user.getUsername());
  }

  @Override
  @CacheEvict(value = "games", allEntries = true)
  public User userLogin(UserCreateResource userCreateResource, BindingResult bindingResult) {
    User user = this.userRepository.findByUsername(userCreateResource.getUsername());
    if (bindingResult.hasErrors()) {
      throw new ValidationException(getAllBindingsErrors(bindingResult).toString());
    } else if (user == null) {
      throw new NullPointerException("Bad credentials");
    }
    authenticate(userCreateResource);
    return user;
  }

  public User getCurrentUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication.getName().equals("anonymousUser")) {
      throw new LoginException("There is no logged user");
    }
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    return this.userRepository.findByUsername(userDetails.getUsername());
  }

  @Override public void setCurrentGame(Game currentGame, Long userId) {
    this.userRepository.setCurrentGame(currentGame, userId);
  }

  @Override
  @CacheEvict(value = "users")
  public void setCurrentGameToCurrentUserToNull() {
    this.userRepository.setCurrentGame(null, getCurrentUser().getId());
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

  @Override
  public User getUserByUsername(String username) {
    return this.userRepository.findByUsername(username);
  }

  @Override
  @Cacheable(value = "users")
  public List<UserBestGameResource> getAllUserWithBestGame() {
    return this.userRepository.getAllUsersByGames();
  }

  public void authenticate(UserCreateResource userCreateResource) {
    UserDetails principal =
      this.gameUserDetailService.loadUserByUsername(userCreateResource.getUsername());
    if (!this.passwordEncoder.matches(userCreateResource.getPassword(), principal.getPassword())) {
      throw new LoginException("Bad credentials!!!");
    }
    Authentication authentication = new UsernamePasswordAuthenticationToken(
      principal, userCreateResource.getPassword(), principal.getAuthorities());
    SecurityContextHolder.getContext().setAuthentication(authentication);
  }
}
