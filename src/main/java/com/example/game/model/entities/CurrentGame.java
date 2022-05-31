package com.example.game.model.entities;

public class CurrentGame {

  private Long id;
  private Integer[] serverNumber;

  public CurrentGame() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Integer[] getServerNumber() {
    return serverNumber;
  }

  public void setServerNumber(Integer[] serverNumber) {
    this.serverNumber = serverNumber;
  }
}
