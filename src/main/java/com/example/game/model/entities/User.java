package com.example.game.model.entities;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.Size;
import com.example.game.web.resources.UserBestGameResource;

@SqlResultSetMapping(
  name = "myMapping",
  classes = {
    @ConstructorResult(
      targetClass = UserBestGameResource.class,
      columns = {
        @ColumnResult(name = "username", type = String.class),
        @ColumnResult(name = "numberOfCompletedGames", type = String.class),
        @ColumnResult(name = "bestNumberOfAttempts", type = String.class),
        @ColumnResult(name = "bestTime", type = String.class)
      }
    )
  }
)
@NamedNativeQuery(name = "getAllUsersByGames",
  resultSetMapping = "myMapping",
  query = "select u.username as username,\n" +
    "    (select count(game) from games as game where game.end_date is not null and game.user_id = u.id) as numberOfCompletedGames ,\n" +
    "    (select game.number_of_attempts from games as game where game.end_date is not null and game.user_id = u.id order by game.number_of_attempts limit 1) as bestNumberOfAttempts,\n" +
    "    (select substring(cast((game.end_date - game.start_date) as varchar) from 0 for 9) from games as game where game.end_date is not null and game.user_id = u.id order by (game.end_date - game.start_date) limit 1) as bestTime\n" +
    "    from users as u\n" +
    "    where (select count(game) from games as game where game.end_date is not null and game.user_id = u.id) != 0\n" +
    "    order by\n" +
    "    bestNumberOfAttempts, bestTime, numberOfCompletedGames;")

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
