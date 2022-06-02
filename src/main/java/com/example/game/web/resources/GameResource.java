package com.example.game.web.resources;

import java.time.LocalDateTime;

public class GameResource {

  private Long id;
  private String serverNumber;
  private String startDate;
  private Long numberOfAttempts;
  private String endDate;
  private boolean isCompleted;

  public GameResource() {
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

  public Long getNumberOfAttempts() {
    return numberOfAttempts;
  }

  public void setNumberOfAttempts(Long numberOfAttempts) {
    this.numberOfAttempts = numberOfAttempts;
  }

  public String getStartDate() {
    return startDate;
  }

  public void setStartDate(String startDate) {
    this.startDate = startDate;
  }

  public String getEndDate() {
    return endDate;
  }

  public void setEndDate(String endDate) {
    this.endDate = endDate;
  }

  public boolean isCompleted() {
    return isCompleted;
  }

  public void setCompleted(boolean completed) {
    isCompleted = completed;
  }
}
