package com.example.game.web.resources;


public class UserBestGameResource {
  private String username;
  private String numberOfCompletedGames;
  private String bestNumberOfAttempts;
  private String bestTime;

  public UserBestGameResource() {
  }

  public UserBestGameResource(String username, String numberOfCompletedGames,
    String bestNumberOfAttempts,
    String bestTime) {
    this.username = username;
    this.numberOfCompletedGames = numberOfCompletedGames;
    this.bestNumberOfAttempts = bestNumberOfAttempts;
    this.bestTime = bestTime;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getNumberOfCompletedGames() {
    return numberOfCompletedGames;
  }

  public void setNumberOfCompletedGames(String numberOfCompletedGames) {
    this.numberOfCompletedGames = numberOfCompletedGames;
  }

  public String getBestNumberOfAttempts() {
    return bestNumberOfAttempts;
  }

  public void setBestNumberOfAttempts(String bestNumberOfAttempts) {
    this.bestNumberOfAttempts = bestNumberOfAttempts;
  }

  public String getBestTime() {
    return bestTime;
  }

  public void setBestTime(String bestTime) {
    this.bestTime = bestTime;
  }
}
