package com.example.game.services.impl;

import java.util.Collections;
import com.example.game.model.entities.User;
import com.example.game.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
public class GameUserDetailService implements UserDetailsService {

  private final UserRepository userRepository;

  @Autowired
  public GameUserDetailService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String name) {
    User user = this.userRepository.findByUsername(name);
    GrantedAuthority authorities = new SimpleGrantedAuthority("USER");
    UserDetails result =
      new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
        Collections.singleton(authorities));
    return result;
  }
}
