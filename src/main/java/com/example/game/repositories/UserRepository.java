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

  @Query(value = "select u.username as username,\n" +
    "(select count(game) from games as game where game.end_date is not null and game.user_id = u.id) as numberOfCompletedGames ,\n" +
    "(select game.number_of_attempts from games as game where game.end_date is not null and game.user_id = u.id order by game.number_of_attempts limit 1) as bestNumberOfAttempts,\n" +
    "(select substring(cast((game.end_date - game.start_date) as varchar) from 0 for 9) from games as game where game.end_date is not null and game.user_id = u.id order by (game.end_date - game.start_date) limit 1) as bestTime\n" +
    "from users as u\n" +
    "where (select count(game) from games as game where game.end_date is not null and game.user_id = u.id) != 0\n" +
    "order by\n" +
    "bestNumberOfAttempts, bestTime, numberOfCompletedGames;", nativeQuery = true)
  List<UserBestGameResource> getAllUsersByGames();

}
