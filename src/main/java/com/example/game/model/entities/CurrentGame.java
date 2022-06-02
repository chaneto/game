package com.example.game.model.entities;

public class CurrentGame {

  private Long id;
  private String serverNumber;

  public CurrentGame() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getServerNumber() {
    return serverNumber;
  }

  public void setServerNumber(String serverNumber) {
    this.serverNumber = serverNumber;
  }
}
