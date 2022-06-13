package com.example.game.configs;

import com.example.game.services.impl.GameUserDetailService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig {

  private final PasswordEncoder passwordEncoder;
  private final GameUserDetailService gameUserDetailService;

  public ApplicationSecurityConfig(
    PasswordEncoder passwordEncoder,
    GameUserDetailService gameUserDetailService) {
    this.passwordEncoder = passwordEncoder;
    this.gameUserDetailService = gameUserDetailService;
  }

  @Bean
  protected SecurityFilterChain configure(HttpSecurity httpSecurity) throws Exception {
    httpSecurity.authorizeRequests()
      .and()
      .csrf()
      .disable()
      .authorizeRequests()
      .antMatchers("/js/**", "/css/**", "/img/**").permitAll()
      .antMatchers("/", "/users/all", "/users/login", "/users/register", "/isLogged").permitAll()
      .antMatchers("/**")
      .permitAll()
      .anyRequest()
      .authenticated()
      .and()
      .logout()
      .logoutUrl("/logout")
      .logoutSuccessUrl("/")
      .invalidateHttpSession(true)
      .deleteCookies("JSESSIONID");
    return httpSecurity.build();
  }

  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(this.gameUserDetailService)
      .passwordEncoder(this.passwordEncoder);
  }

}
