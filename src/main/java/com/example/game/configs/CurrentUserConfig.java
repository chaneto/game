package com.example.game.configs;

import com.example.game.model.entities.CurrentUser;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CurrentUserConfig {

  @Bean
  public CurrentUser currentUser(){
    return new CurrentUser();
  }
}
