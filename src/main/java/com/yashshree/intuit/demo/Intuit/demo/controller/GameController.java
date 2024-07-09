package com.yashshree.intuit.demo.Intuit.demo.controller;

import com.yashshree.intuit.demo.Intuit.demo.entity.ScoreBoard;
import com.yashshree.intuit.demo.Intuit.demo.services.ScoreIngestionSevice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class GameController {

    @Autowired
    ScoreIngestionSevice scoreIngestor;
    Logger logger = LoggerFactory.getLogger(GameController.class);
    @PostMapping("/addScore")
    public void updateScore(@RequestBody ScoreBoard currentScore) {
        try {
            scoreIngestor.publish(currentScore);
        } catch (Exception e) {
            logger.error("Leaderboard Update failed - " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
