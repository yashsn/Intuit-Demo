package com.yashshree.intuit.demo.Intuit.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeaderBoard {
    private int boardID;
    private String topic;
    private int leaderBoardSize;
}
