package com.example.game.model.entities;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Entity
@Table(name ="game_history")
public class CowsAndBulls {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  @Size(min = 4, max = 4, message = "Number must be exactly 4 digits!!!")
  private String number;

  @Column(nullable = false)
  @Min(value = 0, message = "Cows cannot be negative number!!!")
  private Integer cows;

  @Column(nullable = false)
  @Min(value = 0, message = "Bulls cannot be negative number!!!")
  private Integer bulls;

  @ManyToOne
  @JoinColumn(name = "game_id", referencedColumnName = "id")
  private Game game;

  public CowsAndBulls() {
  }

  public CowsAndBulls(String number, Integer cows, Integer bulls,
    Game game) {
    this.number = number;
    this.cows = cows;
    this.bulls = bulls;
    this.game = game;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Game getGame() {
    return game;
  }

  public void setGame(Game game) {
    this.game = game;
  }

  public String getNumber() {
    return number;
  }

  public void setNumber(String number) {
    this.number = number;
  }

  public Integer getCows() {
    return cows;
  }

  public void setCows(Integer cows) {
    this.cows = cows;
  }

  public Integer getBulls() {
    return bulls;
  }

  public void setBulls(Integer bulls) {
    this.bulls = bulls;
  }
}
