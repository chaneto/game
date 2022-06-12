package com.example.game.web;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import io.swagger.annotations.ApiOperation;

@Controller
public class HomeController {

  @ApiOperation(httpMethod = "GET", value = "Home page.")
  @GetMapping("/")
  public String index() {
    return "index.html";
  }

  @GetMapping("/isLogged")
  @ResponseBody
  public boolean isLogged(){
    boolean isLogged = true;
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication.getName().equals("anonymousUser")) {
      isLogged = false;
    }
    return isLogged;
  }
}
