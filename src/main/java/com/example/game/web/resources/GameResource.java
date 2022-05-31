package com.example.game.web.resources;

import java.time.LocalDateTime;

public class GameResource {

  private LocalDateTime startDate;
  private Long numberOfAttempts;
  private LocalDateTime endDate;
  private boolean isCompleted;

  public GameResource() {
  }

  public LocalDateTime getStartDate() {
    return startDate;
  }

  public void setStartDate(LocalDateTime startDate) {
    this.startDate = startDate;
  }

  public Long getNumberOfAttempts() {
    return numberOfAttempts;
  }

  public void setNumberOfAttempts(Long numberOfAttempts) {
    this.numberOfAttempts = numberOfAttempts;
  }

  public LocalDateTime getEndDate() {
    return endDate;
  }

  public void setEndDate(LocalDateTime endDate) {
    this.endDate = endDate;
  }

  public boolean isCompleted() {
    return isCompleted;
  }

  public void setCompleted(boolean completed) {
    isCompleted = completed;
  }
}
