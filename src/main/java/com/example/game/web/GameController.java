package com.example.game.web;

import java.util.List;
import javax.validation.Valid;
import com.example.game.model.entities.CowsAndBulls;
import com.example.game.model.entities.Game;
import com.example.game.services.GameService;
import com.example.game.web.assembler.GameAssembler;
import com.example.game.web.resources.GameResource;
import com.example.game.web.resources.NumberResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/games")
public class GameController {

  private final GameService gameService;
  private final GameAssembler gameAssembler;

  public GameController(GameService gameService,
    GameAssembler gameAssembler) {
    this.gameService = gameService;
    this.gameAssembler = gameAssembler;
  }

  @GetMapping("/history/{id}")
  public ResponseEntity<?> getGameHistory(@PathVariable Long id){
    List<CowsAndBulls> cowsAndBulls = this.gameService.getGameHistory(id);
    return new ResponseEntity<>(this.gameAssembler.assembleCowsAndBullsResource(cowsAndBulls), HttpStatus.OK);
  }

  @PostMapping("/create")
  public ResponseEntity<?> createGame(){
    return new ResponseEntity<>(this.gameAssembler.assembleGameResource(this.gameService.createGame()), HttpStatus.CREATED);
  }

  @PostMapping("/compare")
  public ResponseEntity<?> compare(@RequestBody @Valid NumberResource numberResource, BindingResult bindingResult){
    List<CowsAndBulls> cowsAndBulls = this.gameService.compare(numberResource, bindingResult);
    return new ResponseEntity<>(this.gameAssembler.assembleCowsAndBullsResource(cowsAndBulls), HttpStatus.OK);
  }

  @GetMapping
  public List<GameResource> getAllUserGame(){
    List<Game> games = this.gameService.findAllByUserId();
    return this.gameAssembler.assembleGamesResource(games);
  }

  @GetMapping("/continue/{id}")
  public ResponseEntity<?> gameContinue(@PathVariable  Long id){
    return new ResponseEntity<>(this.gameAssembler.assembleGameResource(this.gameService.continueGame(id)), HttpStatus.CREATED);
  }
}
