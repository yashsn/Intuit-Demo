package com.yashshree.intuit.demo.Intuit.demo.services.logic;

import com.yashshree.intuit.demo.Intuit.demo.entity.ScoreBoard;

import java.util.Comparator;

public interface LeaderBoardLogic extends Comparator<ScoreBoard> {
    public int compareTo(ScoreBoard score1, ScoreBoard score2);
}
