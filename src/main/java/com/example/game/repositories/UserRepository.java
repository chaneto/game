package com.example.game.repositories;

import java.util.List;
import com.example.game.model.entities.Game;
import com.example.game.model.entities.User;
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

  @Query(value = "select u.username,(select count(game) from games as game\n" +
    "where game.end_date is not null and game.user_id = u.id) ,(select game.number_of_attempts from games as game\n" +
    "where game.end_date is not null and game.user_id = u.id\n" +
    "order by game.number_of_attempts limit 1)," +
    "(select cast((end_date - start_date) as varchar) from games as game\n" +
    "where game.end_date is not null and game.user_id = u.id\n" +
    "order by game.number_of_attempts, (game.end_date - game.start_date) limit 1)" +
    "from users as u\n" +
    "order by (select game.number_of_attempts from games as game\n" +
    "where game.end_date is not null and game.user_id = u.id\n" +
    "order by game.number_of_attempts limit 1), \n" +
    "(select (game.end_date - game.start_date) from games as game\n" +
    "where game.end_date is not null and game.user_id = u.id\n" +
    "order by game.number_of_attempts, (game.end_date - game.start_date) limit 1);", nativeQuery = true)
  List<String[]> getAllUsersByGames();

}
