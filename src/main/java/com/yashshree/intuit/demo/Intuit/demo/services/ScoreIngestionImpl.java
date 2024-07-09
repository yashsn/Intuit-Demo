package com.yashshree.intuit.demo.Intuit.demo.services;

import com.yashshree.intuit.demo.Intuit.demo.entity.ScoreBoard;
import com.yashshree.intuit.demo.Intuit.demo.exceptions.DatabaseStorageException;
import com.yashshree.intuit.demo.Intuit.demo.exceptions.LeaderboardUpdateFailureException;
import com.yashshree.intuit.demo.Intuit.demo.repository.ScoreBoardRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ScoreIngestionImpl implements ScoreIngestionSevice, ScoreToLBoard, ScoreToDB {
    List<LeaderBoardService> allLeaderBoards = new ArrayList<>();
    @Autowired
    ScoreBoardRepository scoreBoardRepository;

    @Override
    @Transactional
    public void publish(ScoreBoard score) throws LeaderboardUpdateFailureException, DatabaseStorageException {
        //the main publish method which publishes to LB and DB
        publishToLeaderBoards(score);
        publishToDB(score);
    }

    @Override
    public void registerLeaderBoard(LeaderBoardService leaderBoard) {
        allLeaderBoards.add(leaderBoard);
    }

    @Override
    public void publishToLeaderBoards(ScoreBoard score) throws LeaderboardUpdateFailureException {
        for(LeaderBoardService leaderBoardService:allLeaderBoards){
            leaderBoardService.publish(score);
        }
    }

    @Override
    public void publishToDB(ScoreBoard score) throws DatabaseStorageException {
        try {
            Optional<ScoreBoard> scoreAlreadyPresent = scoreBoardRepository.findById(score.getUsername());
            if (scoreAlreadyPresent.isPresent() && scoreAlreadyPresent.get().getScore() >= score.getScore()) {
                return;
            }
            scoreBoardRepository.save(score);
        } catch (Exception e) {
            throw new DatabaseStorageException("Data not added to database due to error");
        }
    }
}
