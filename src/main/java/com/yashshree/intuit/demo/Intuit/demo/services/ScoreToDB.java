package com.yashshree.intuit.demo.Intuit.demo.services;

import com.yashshree.intuit.demo.Intuit.demo.entity.ScoreBoard;
import com.yashshree.intuit.demo.Intuit.demo.exceptions.DatabaseStorageException;

public interface ScoreToDB {
    public void publishToDB(ScoreBoard score) throws DatabaseStorageException;
}

