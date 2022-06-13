package com.example.game.repositories;

import java.util.List;
import com.example.game.model.entities.Game;
import com.example.game.model.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

  List<Game> findAllByUserId(Long id);

  Page<Game> findAllByUserId(Long id, Pageable pageable);

  @Modifying
  @Transactional
  @Query("update Game as g set g.user = :user where g.id = :id")
  void setUser(@Param("user") User user, @Param("id") Long id);
}
