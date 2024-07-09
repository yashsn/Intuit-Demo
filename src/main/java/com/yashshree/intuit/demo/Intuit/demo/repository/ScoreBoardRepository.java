package com.yashshree.intuit.demo.Intuit.demo.repository;

import com.yashshree.intuit.demo.Intuit.demo.entity.ScoreBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScoreBoardRepository extends JpaRepository<ScoreBoard,String> {
}
