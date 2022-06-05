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

  void deleteById(Long id);

  @Modifying
  @Transactional
  @Query("update User as u set u.currentGame = :currentGame where u.id = :id")
  void setCurrentGame(@Param("currentGame") Game currentGame, @Param("id") Long id);

  @Query(value = "select u.username,\n" +
    "(select count(game) from games as game where game.end_date is not null and game.user_id = u.id) as completed_games ,\n" +
    "(select game.number_of_attempts from games as game where game.end_date is not null and game.user_id = u.id order by game.number_of_attempts limit 1) as number_of_attempts,\n" +
    "(select cast((game.end_date - game.start_date) as varchar) from games as game where game.end_date is not null and game.user_id = u.id order by (game.end_date - game.start_date) limit 1) as best_time\n" +
    "from users as u\n" +
    "order by number_of_attempts, best_time;", nativeQuery = true)
  List<String[]> getAllUsersByGames();

}
