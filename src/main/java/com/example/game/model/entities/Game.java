package com.example.game.model.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.Size;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "games")
public class Game {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "server_number", nullable = false)
  @Size(min = 4, max = 4, message = "Number must be exactly 4 digits!!!")
  private String serverNumber;

  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @Column(name = "start_date", nullable = false)
  private LocalDateTime startDate;

  @Column(name = "number_of_attempts", nullable = false)
  private Long numberOfAttempts;

  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @Column(name = "end_date")
  private LocalDateTime endDate;

  @Column(name = "is_completed", nullable = false)
  private boolean isCompleted;

  @OneToMany(mappedBy = "game", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
  private List<CowsAndBulls> gameHistory = new ArrayList<>();

  @ManyToOne
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  private User user;

  public Game() {
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

  public List<CowsAndBulls> getGameHistory() {
    return gameHistory;
  }

  public void setGameHistory(List<CowsAndBulls> gameHistory) {
    this.gameHistory = gameHistory;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }
}


