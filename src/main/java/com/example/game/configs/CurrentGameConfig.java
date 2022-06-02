package com.example.game.configs;

import com.example.game.model.entities.CurrentGame;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CurrentGameConfig {

  @Bean
  public CurrentGame currentGame(){
    return new CurrentGame();
  }
}
