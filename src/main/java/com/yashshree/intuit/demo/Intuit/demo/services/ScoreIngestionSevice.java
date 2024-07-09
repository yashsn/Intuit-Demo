package com.yashshree.intuit.demo.Intuit.demo.services;

import com.yashshree.intuit.demo.Intuit.demo.entity.ScoreBoard;
import com.yashshree.intuit.demo.Intuit.demo.exceptions.*;

public interface ScoreIngestionSevice {
    public void publish(ScoreBoard score) throws LeaderboardUpdateFailureException, DatabaseStorageException;
}
