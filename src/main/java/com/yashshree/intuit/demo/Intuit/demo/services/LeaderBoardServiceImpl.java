package com.yashshree.intuit.demo.Intuit.demo.services;

import com.yashshree.intuit.demo.Intuit.demo.constants.Constants;
import com.yashshree.intuit.demo.Intuit.demo.entity.ScoreBoard;
import com.yashshree.intuit.demo.Intuit.demo.exceptions.*;
import com.yashshree.intuit.demo.Intuit.demo.repository.ScoreBoardRepository;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeaderBoardServiceImpl implements LeaderBoardService{
    @Autowired
    ScoreBoardRepository scoreBoardRepository;
    @Autowired
    CacheService<ScoreBoard> cache;
    //add score injestor
    @Autowired
    ScoreToLBoard scoreIngestor;

    boolean boardInitialized;
    Logger logger = LoggerFactory.getLogger(LeaderBoardServiceImpl.class);

    @Override
    public void createBoard(int topN) throws LeaderboardNotInitializedException{
        initializeBoard(topN);
    }
    public void createBoard() throws LeaderboardNotInitializedException{
        initializeBoard(Constants.DEFAULT_LEADERBOARD_SIZE);
    }
    private void initializeBoard(int topN) throws LeaderboardNotInitializedException {
        try {
            List<ScoreBoard> allScores = scoreBoardRepository.findAll();
            cache.initialize(topN, allScores);
            scoreIngestor.registerLeaderBoard(this);
            boardInitialized = true;
        } catch (CacheInitializationException e) {
            logger.error("Initialization for Leader Board has failed - " + e.getMessage());
            throw new LeaderboardNotInitializedException(e.getMessage());
        }
    }

    @Override
    public List<ScoreBoard> getTopNPlayers() throws LeaderboardNotInitializedException {
        if (!boardInitialized) {
            logger.error("Board Not Initialized - Cannot list of players");
            throw new LeaderboardNotInitializedException("LeaderBoard not yet initialized");
        }
        return cache.getTopNplayers();
    }

    @Override
    public void publish(ScoreBoard score){
        try {
            cache.addtoCache(score);
        } catch (CacheUpdateFailureException e) {
            logger.error("Leader Board Update failed - " + e.getMessage());
            //throw new LeaderboardUpdateFailureException(e.getMessage());
        }
    }
}
