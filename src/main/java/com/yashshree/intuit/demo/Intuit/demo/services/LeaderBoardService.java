package com.yashshree.intuit.demo.Intuit.demo.services;

import com.yashshree.intuit.demo.Intuit.demo.entity.ScoreBoard;
import com.yashshree.intuit.demo.Intuit.demo.exceptions.*;

import java.util.List;

public interface LeaderBoardService {
    public void createBoard(int topN) throws CacheInitializationException, LeaderboardNotInitializedException;
    public List<ScoreBoard> getTopNPlayers() throws LeaderboardNotInitializedException;
    public void publish(ScoreBoard score) throws LeaderboardUpdateFailureException;
}
