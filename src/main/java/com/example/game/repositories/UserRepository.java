package com.example.game.repositories;

import java.util.List;

import com.example.game.model.entities.Game;
import com.example.game.model.entities.User;
import com.example.game.web.resources.UserBestGameResource;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  User findByUsername(String username);

  @Modifying
  @Transactional
  @Query("update User as u set u.currentGame = :currentGame where u.id = :id")
  void setCurrentGame(@Param("currentGame") Game currentGame, @Param("id") Long id);

  @Query(name = "getAllUsersByGames", nativeQuery = true)
  List<UserBestGameResource> getAllUsersByGames();

}
