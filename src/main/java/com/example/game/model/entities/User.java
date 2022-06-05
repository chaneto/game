package com.example.game.model.entities;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "users")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Size(min = 2, max = 20, message = "Username length must be between 2 and 20 characters!!!")
  @Column(unique = true, nullable = false)
  private String username;

  @Column(nullable = false)
  @Size(min = 5, message = "Username length must be min 5 characters!!!")
  private String password;

  @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Game> games = new ArrayList<>();

  @OneToOne
  private Game currentGame;

  public User() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public List<Game> getGames() {
    return games;
  }

  public void setGames(List<Game> games) {
    this.games = games;
  }

  public Game getCurrentGame() {
    return currentGame;
  }

  public void setCurrentGame(Game currentGame) {
    this.currentGame = currentGame;
  }
}
