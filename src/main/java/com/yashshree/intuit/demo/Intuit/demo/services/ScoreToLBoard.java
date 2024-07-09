package com.yashshree.intuit.demo.Intuit.demo.services;

import com.yashshree.intuit.demo.Intuit.demo.entity.ScoreBoard;
import com.yashshree.intuit.demo.Intuit.demo.exceptions.LeaderboardUpdateFailureException;

public interface ScoreToLBoard {
    public void registerLeaderBoard(LeaderBoardService leaderBoard);
    public void publishToLeaderBoards(ScoreBoard newScore) throws LeaderboardUpdateFailureException;
}
