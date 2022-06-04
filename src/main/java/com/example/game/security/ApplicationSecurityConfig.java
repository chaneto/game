package com.example.game.security;

import com.example.game.services.impl.GameUserDetailService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

  private final PasswordEncoder passwordEncoder;
  private final GameUserDetailService gameUserDetailService;

  public ApplicationSecurityConfig(
    PasswordEncoder passwordEncoder,
    GameUserDetailService gameUserDetailService) {
    this.passwordEncoder = passwordEncoder;
    this.gameUserDetailService = gameUserDetailService;
  }

  @Override
  protected void configure(HttpSecurity httpSecurity) throws Exception {
    httpSecurity.authorizeRequests()
      .and()
      .csrf()
      .disable()
      .authorizeRequests()
      .antMatchers("/js/**", "/css/**", "/img/**").permitAll()
      .antMatchers("/", "/users/all", "/users/login", "/users/register", "/users/games")
      .permitAll()
      .anyRequest()
      .authenticated()
      .and()
      .formLogin()
      .usernameParameter("username")
      .passwordParameter("password")
      .defaultSuccessUrl("/all")
      .and()
      .logout()
      .logoutUrl("/logout")
      .logoutSuccessUrl("/")
      .invalidateHttpSession(true)
      .deleteCookies("JSESSIONID");
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(this.gameUserDetailService)
      .passwordEncoder(this.passwordEncoder);
  }

}
