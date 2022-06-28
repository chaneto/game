package com.example.game.web;

import java.util.List;
import javax.validation.Valid;
import com.example.game.model.entities.CowsAndBulls;
import com.example.game.model.entities.Game;
import com.example.game.services.GameService;
import com.example.game.web.assembler.GameAssembler;
import com.example.game.web.resources.CowsAndBullsResource;
import com.example.game.web.resources.GameResource;
import com.example.game.web.resources.NumberResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/games")
public class GameController {

  private final GameService gameService;
  private final GameAssembler gameAssembler;

  @Autowired
  public GameController(GameService gameService,
    GameAssembler gameAssembler) {
    this.gameService = gameService;
    this.gameAssembler = gameAssembler;
  }

  @ApiOperation(httpMethod = "GET", value = "Game history.", response = CowsAndBullsResource.class)
  @GetMapping("/history/{id}")
  public ResponseEntity<List<CowsAndBullsResource>> getGameHistory(@PathVariable Long id) {
    List<CowsAndBulls> cowsAndBulls = this.gameService.getGameHistory(id);
    return new ResponseEntity<>(this.gameAssembler.assembleCowsAndBullsResource(cowsAndBulls),
      HttpStatus.OK);
  }

  @ApiOperation(httpMethod = "POST", value = "Create new Game.", response = GameResource.class)
  @PostMapping("/create")
  public ResponseEntity<GameResource> createGame() {
    return new ResponseEntity<>(
      this.gameAssembler.assembleGameResource(this.gameService.createGame()), HttpStatus.CREATED);
  }

  @ApiOperation(httpMethod = "POST", value = "Compare the numbers, how many cows and bulls.", response = CowsAndBullsResource.class)
  @PostMapping("/compare")
  public ResponseEntity<List<CowsAndBullsResource>> compare(
    @RequestBody @Valid NumberResource numberResource, BindingResult bindingResult) {
    List<CowsAndBulls> cowsAndBulls = this.gameService.compare(numberResource, bindingResult);
    return new ResponseEntity<>(this.gameAssembler.assembleCowsAndBullsResource(cowsAndBulls),
      HttpStatus.OK);
  }

  @ApiOperation(httpMethod = "GET", value = "Get all User games.", response = GameResource.class)
  @GetMapping
  public ResponseEntity<List<GameResource>> getAllUserGame(@RequestParam("page") Integer page,
    @RequestParam("pageSize") Integer pageSize) {
    List<Game> games = this.gameService.findAllByUserId(page, pageSize);
    return new ResponseEntity<>(this.gameAssembler.assembleGamesResource(games), HttpStatus.OK);
  }

  @ApiOperation(httpMethod = "GET", value = "Get all User games size.", response = GameResource.class)
  @GetMapping("/size")
  public List<GameResource> getAllUserGameSize() {
    List<Game> games = this.gameService.findAllCurrentUserGames();
    return this.gameAssembler.assembleGamesResource(games);
  }

  @ApiOperation(httpMethod = "GET", value = "Continue unfinished game", response = GameResource.class)
  @GetMapping("/continue/{id}")
  public ResponseEntity<GameResource> gameContinue(@PathVariable Long id) {
    return new ResponseEntity<>(
      this.gameAssembler.assembleGameResource(this.gameService.continueGame(id)), HttpStatus.OK);
  }
}
