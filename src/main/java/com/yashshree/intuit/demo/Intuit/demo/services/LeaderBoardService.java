package com.yashshree.intuit.demo.Intuit.demo.services;

import com.yashshree.intuit.demo.Intuit.demo.entity.ScoreBoard;
import com.yashshree.intuit.demo.Intuit.demo.exceptions.*;

import java.util.List;

public interface LeaderBoardService {
    void createBoard(int topN) throws CacheInitializationException, LeaderboardNotInitializedException;
    List<ScoreBoard> getTopNPlayers() throws LeaderboardNotInitializedException;
    void publish(ScoreBoard score) throws LeaderboardUpdateFailureException;
}
