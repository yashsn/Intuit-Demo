package com.yashshree.intuit.demo.Intuit.demo.controller;

import com.yashshree.intuit.demo.Intuit.demo.entity.LeaderBoard;
import com.yashshree.intuit.demo.Intuit.demo.entity.ScoreBoard;
import com.yashshree.intuit.demo.Intuit.demo.exceptions.LeaderboardNotInitializedException;
import com.yashshree.intuit.demo.Intuit.demo.services.LeaderBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class LeaderBoardController {
    @Autowired
    LeaderBoardService leaderBoard;
    Logger logger = LoggerFactory.getLogger(LeaderBoardController.class);

    public LeaderBoardController(LeaderBoardService leaderBoard) {
        this.leaderBoard = leaderBoard;
    }

    @PostMapping("/createBoard")
    public void createBoard(@RequestBody LeaderBoard in) {
        try {
            leaderBoard.createBoard(in.getLeaderBoardSize());
        } catch (Exception e) {
            logger.error("Leaderboard creation failed - " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping("/getTopScorers")
    public List<ScoreBoard> getTopScorers() {
        try {
            return leaderBoard.getTopNPlayers();
        } catch (LeaderboardNotInitializedException e) {
            logger.error("Leaderboard not initialized - " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Please register/create LeaderBoard first");
        } catch (Exception e) {
            logger.error("Couldn't get top scores - " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
