package com.example.game.repositories;

import java.util.List;
import com.example.game.model.entities.Game;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

   List<Game> findAllByUserIdOrderByIdDesc(Long id);

   Page<Game> findAllByUserId(Long id, Pageable pageable);
}
