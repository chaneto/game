package com.example.game.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import io.swagger.annotations.ApiOperation;

@Controller
public class HomeController {

  @ApiOperation(httpMethod = "GET", value = "Home page.")
  @GetMapping("/")
  public String index() {
    return "index.html";
  }
}
