package com.example.game.web.resources;

public class CowsAndBullsResource {

  private String number;
  private Integer cows;
  private Integer bulls;

  public CowsAndBullsResource() {
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
