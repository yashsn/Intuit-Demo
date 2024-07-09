package com.yashshree.intuit.demo.Intuit.demo.services;

import com.yashshree.intuit.demo.Intuit.demo.constants.Constants;
import com.yashshree.intuit.demo.Intuit.demo.entity.ScoreBoard;
import com.yashshree.intuit.demo.Intuit.demo.exceptions.CacheInitializationException;
import com.yashshree.intuit.demo.Intuit.demo.exceptions.CacheUpdateFailureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CacheServiceImpl implements CacheService<ScoreBoard> {
    private int rankers;
    PriorityQueue<ScoreBoard> scorePQ;
    HashMap<String, ScoreBoard> usernameToScore;
    Logger logger = LoggerFactory.getLogger(CacheServiceImpl.class);

    @Override
    public void initialize(int topN, List<ScoreBoard> data) throws CacheInitializationException {
        this.rankers = topN;
        try {
            this.scorePQ = new PriorityQueue<>();//max heap
            //this.scorePQ=new PriorityQueue<>(this.rankers,(a,b)->b.getScore()-a.getScore());//max heap
            this.usernameToScore = new HashMap<>();
            for (ScoreBoard score : data) {
//            this.scorePQ.add(score);
//            if(this.usernameToScore.containsKey(score.getUsername())){
//                if(this.usernameToScore.get(score.getUsername()))
//            }
                if (this.scorePQ.size() < rankers) {
                    this.scorePQ.offer(score);
                    usernameToScore.put(score.getUsername(), score);
                } else {
                    assert this.scorePQ.peek() != null;
                    if (score.getScore() > this.scorePQ.peek().getScore()) {
                        ScoreBoard removedScore = this.scorePQ.poll();
                        this.scorePQ.add(score);
                        usernameToScore.remove(removedScore.getUsername());
                        usernameToScore.put(score.getUsername(), score);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Failed to initialize cache - " + e.getMessage());
            throw new CacheInitializationException("Failed to initialize cache");
        }
    }

    @Override
    public void addtoCache(ScoreBoard data) throws CacheUpdateFailureException {
        try {
            if (this.usernameToScore.containsKey(data.getUsername())) {
                ScoreBoard scoreToBeUpdated = usernameToScore.get(data.getUsername());

                if (scoreToBeUpdated.getScore() < data.getScore()) {
                    //logger.debug("Updating " + scoreToBeUpdated.getPlayerId() + " to " + score.getScore());
                    this.scorePQ.remove(scoreToBeUpdated);
                    this.usernameToScore.put(data.getUsername(), data);
                    this.scorePQ.add(data);
                }
                return;
            }
            if (this.scorePQ.size() < this.rankers) {
                this.scorePQ.add(data);
                this.usernameToScore.put(data.getUsername(), data);
            } else {
                if (data.getScore() > this.scorePQ.peek().getScore()) {
                    ScoreBoard removedScore = this.scorePQ.poll();
                    this.scorePQ.add(data);
                    this.usernameToScore.remove(removedScore.getUsername());
                    this.usernameToScore.put(data.getUsername(), data);
                }
            }
        } catch (Exception e) {
            logger.error("Failed to update cache - " + e.getMessage());
            throw new CacheUpdateFailureException(e.getMessage());
        }
    }

    @Override
    public List<ScoreBoard> getTopNplayers() {

        List<ScoreBoard> res = new ArrayList<ScoreBoard>(this.scorePQ);
        Collections.sort(res, Collections.reverseOrder());
        return res;
    }
}
