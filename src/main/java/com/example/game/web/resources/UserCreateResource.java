package com.example.game.web.resources;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UserCreateResource {

  @NotBlank
  @Size(min = 2, max = 20, message = "Username length must be between 2 and 20 characters!!!")
  private String username;

  @NotBlank
  @Size(min = 5, message = "Password length must be min 5 characters!!!")
  private String password;

  @Size(min = 5, message = "Password length must be min 5 characters!!!")
  private String confirmPassword;

  public UserCreateResource() {
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

  public String getConfirmPassword() {
    return confirmPassword;
  }

  public void setConfirmPassword(String confirmPassword) {
    this.confirmPassword = confirmPassword;
  }
}
