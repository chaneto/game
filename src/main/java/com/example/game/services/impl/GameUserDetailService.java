package com.example.game.services.impl;

import com.example.game.exceptions.NotFoundException;
import com.example.game.model.entities.User;
import com.example.game.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
public class GameUserDetailService implements UserDetailsService {

  private final UserRepository userRepository;

  public GameUserDetailService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String name)  {
    User user = this.userRepository.findByUsername(name);
    if(user == null){
      throw new NotFoundException("User with" + name + " was not found!!!");
    }
    UserDetails result = org.springframework.security.core.userdetails.User.withUsername(user.getUsername()).password(user.getPassword()).authorities("USER").build();
    return result;
  }
}
