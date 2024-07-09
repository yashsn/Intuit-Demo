package com.yashshree.intuit.demo.Intuit.demo.services;

import com.yashshree.intuit.demo.Intuit.demo.entity.ScoreBoard;
import com.yashshree.intuit.demo.Intuit.demo.exceptions.DatabaseStorageException;
import com.yashshree.intuit.demo.Intuit.demo.exceptions.LeaderboardUpdateFailureException;
import com.yashshree.intuit.demo.Intuit.demo.repository.ScoreBoardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ScoreIngestionImplTest {

    @Mock
    private ScoreBoardRepository scoreBoardRepository;

    @Mock
    private LeaderBoardService leaderBoardService;

    @InjectMocks
    private ScoreIngestionImpl scoreIngestionService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testPublishSuccess() throws LeaderboardUpdateFailureException, DatabaseStorageException {
        ScoreBoard score = new ScoreBoard();
        score.setUsername("user1");
        score.setScore(100);

        doNothing().when(leaderBoardService).publish(score);
        when(scoreBoardRepository.findById(score.getUsername())).thenReturn(Optional.of(new ScoreBoard("hi", 1, 1)));

        scoreIngestionService.registerLeaderBoard(leaderBoardService);
        scoreIngestionService.publish(score);

        verify(leaderBoardService, times(1)).publish(score);
        verify(scoreBoardRepository, times(1)).save(score);
    }

    @Test
    public void testPublishToLeaderBoardsFailure() throws LeaderboardUpdateFailureException, DatabaseStorageException {
        ScoreBoard score = new ScoreBoard();
        score.setUsername("user1");
        score.setScore(100);

        doThrow(new LeaderboardUpdateFailureException("Update failed")).when(leaderBoardService).publish(score);

        scoreIngestionService.registerLeaderBoard(leaderBoardService);

        assertThrows(LeaderboardUpdateFailureException.class, () -> {
            scoreIngestionService.publish(score);
        });

        verify(leaderBoardService, times(1)).publish(score);
    }

    @Test
    public void testPublishToDBFailure() throws LeaderboardUpdateFailureException, DatabaseStorageException {
        ScoreBoard score = new ScoreBoard();
        score.setUsername("user1");
        score.setScore(100);

        doNothing().when(leaderBoardService).publish(score);
        when(scoreBoardRepository.findById(score.getUsername())).thenThrow(new RuntimeException("DB error"));

        scoreIngestionService.registerLeaderBoard(leaderBoardService);

        assertThrows(DatabaseStorageException.class, () -> {
            scoreIngestionService.publish(score);
        });

        verify(leaderBoardService, times(1)).publish(score);
        verify(scoreBoardRepository, times(1)).findById(score.getUsername());
    }

    @Test
    public void testRegisterLeaderBoard() {
        scoreIngestionService.registerLeaderBoard(leaderBoardService);
        assertEquals(1, scoreIngestionService.allLeaderBoards.size());
        assertEquals(leaderBoardService, scoreIngestionService.allLeaderBoards.get(0));
    }

    @Test
    public void testPublishToLeaderBoardsSuccess() throws LeaderboardUpdateFailureException {
        ScoreBoard score = new ScoreBoard();
        scoreIngestionService.registerLeaderBoard(leaderBoardService);

        doNothing().when(leaderBoardService).publish(score);

        scoreIngestionService.publishToLeaderBoards(score);

        verify(leaderBoardService, times(1)).publish(score);
    }

    @Test
    public void testPublishToDBSuccess() throws DatabaseStorageException {
        ScoreBoard score = new ScoreBoard();
        score.setUsername("user4");
        score.setScore(100);
        score.setId(5);


        when(scoreBoardRepository.findById(score.getUsername())).thenReturn(Optional.of(new ScoreBoard("hi", 1, 1)));

        scoreIngestionService.publishToDB(score);

        verify(scoreBoardRepository, times(1)).save(score);
    }
}
