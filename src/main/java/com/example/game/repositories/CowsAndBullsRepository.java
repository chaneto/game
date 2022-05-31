package com.example.game.repositories;

import com.example.game.model.entities.CowsAndBulls;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CowsAndBullsRepository extends JpaRepository<CowsAndBulls, Long> {
}
