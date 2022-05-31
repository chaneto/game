package com.example.game.web.resources;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class NumberResource {

  @Size(min = 4, max = 4, message = "Number must be exactly 4 digits!!!")
  @NotBlank(message = "Number cannot be null or empty!!!")
  private String number;

  public NumberResource() {
  }

  public String getNumber() {
    return number;
  }

  public void setNumber(String number) {
    this.number = number;
  }
}
