package com.yashshree.intuit.demo.Intuit.demo.services;
//
//import com.yashshree.intuit.demo.Intuit.demo.entity.ScoreBoard;
//import com.yashshree.intuit.demo.Intuit.demo.exceptions.CacheInitializationException;
//import com.yashshree.intuit.demo.Intuit.demo.exceptions.LeaderboardNotInitializedException;
//import com.yashshree.intuit.demo.Intuit.demo.repository.ScoreBoardRepository;
//import org.assertj.core.api.Assertions;
//import org.junit.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(SpringExtension.class)
//public class LeaderBoardServiceTest {
//    @Mock
//    private ScoreBoardRepository scoreBoardRepository;
//
//    @InjectMocks
//    private LeaderBoardService leaderBoardService;
//    @Test
//    public void getAllPerson() throws LeaderboardNotInitializedException, CacheInitializationException {
//        ScoreBoard sb=new ScoreBoard("ya",3,400);
//        //when(scoreBoardRepository.save(Mockito.any(ScoreBoard.class))).thenReturn(sb);
//        //when(scoreBoardRepository.findAll()).thenReturn(List.of(sb));
//        Mockito.when(scoreBoardRepository.findAll()).thenReturn(List.of(new ScoreBoard("u1",1,100),
//                new ScoreBoard("u2",2,300),new ScoreBoard("u3",3,10)));
//        List<ScoreBoard> returned=leaderBoardService.getTopNPlayers();
//        Assertions.assertThat(returned).isNotNull();
//    }
//
//}

import com.yashshree.intuit.demo.Intuit.demo.constants.Constants;
import com.yashshree.intuit.demo.Intuit.demo.entity.ScoreBoard;
import com.yashshree.intuit.demo.Intuit.demo.exceptions.*;
import com.yashshree.intuit.demo.Intuit.demo.repository.ScoreBoardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LeaderBoardServiceImplTest {

    @Mock
    private ScoreBoardRepository scoreBoardRepository;

    @Mock
    private CacheService<ScoreBoard> cache;

    @Mock
    private ScoreToLBoard scoreIngestor;

    @Mock
    private Logger logger;

    @InjectMocks
    private LeaderBoardServiceImpl leaderBoardService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateBoardSuccess() throws LeaderboardNotInitializedException, CacheInitializationException {
        List<ScoreBoard> allScores = new ArrayList<>();
        when(scoreBoardRepository.findAll()).thenReturn(allScores);

        leaderBoardService.createBoard(10);

        verify(scoreBoardRepository, times(1)).findAll();
        verify(cache, times(1)).initialize(10, allScores);
        verify(scoreIngestor, times(1)).registerLeaderBoard(leaderBoardService);
        assertTrue(leaderBoardService.boardInitialized);
    }

    @Test
    public void testCreateBoardFailure() throws CacheInitializationException {
        List<ScoreBoard> allScores = new ArrayList<>();
        when(scoreBoardRepository.findAll()).thenReturn(allScores);
        doThrow(new CacheInitializationException("Cache init failed")).when(cache).initialize(10, allScores);

        LeaderboardNotInitializedException exception = assertThrows(LeaderboardNotInitializedException.class, () -> {
            leaderBoardService.createBoard(10);
        });

        assertEquals("Cache init failed", exception.getMessage());
        verify(logger, times(1)).error("Initialization for Leader Board has failed - Cache init failed");
    }

    @Test
    public void testGetTopNPlayersSuccess() throws LeaderboardNotInitializedException {
        List<ScoreBoard> topPlayers = new ArrayList<>();
        when(cache.getTopNplayers()).thenReturn(topPlayers);
        leaderBoardService.boardInitialized = true;

        List<ScoreBoard> result = leaderBoardService.getTopNPlayers();

        assertEquals(topPlayers, result);
        verify(cache, times(1)).getTopNplayers();
    }

    @Test
    public void testGetTopNPlayersNotInitialized() {
        leaderBoardService.boardInitialized = false;

        LeaderboardNotInitializedException exception = assertThrows(LeaderboardNotInitializedException.class, () -> {
            leaderBoardService.getTopNPlayers();
        });

        assertEquals("LeaderBoard not yet initialized", exception.getMessage());
        verify(logger, times(1)).error("Board Not Initialized - Cannot list of players");
    }

    @Test
    public void testPublishSuccess() throws CacheUpdateFailureException {
        ScoreBoard score = new ScoreBoard();
        doNothing().when(cache).addtoCache(score);

        leaderBoardService.publish(score);

        verify(cache, times(1)).addtoCache(score);
    }

    @Test
    public void testPublishFailure() throws CacheUpdateFailureException {
        ScoreBoard score = new ScoreBoard();
        doThrow(new CacheUpdateFailureException("Update failed")).when(cache).addtoCache(score);

        leaderBoardService.publish(score);

        verify(logger, times(1)).error("Leader Board Update failed - Update failed");
    }
}
