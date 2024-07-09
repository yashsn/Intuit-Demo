package com.yashshree.intuit.demo.Intuit.demo.controller;

import com.yashshree.intuit.demo.Intuit.demo.exceptions.CacheInitializationException;
import com.yashshree.intuit.demo.Intuit.demo.exceptions.LeaderboardNotInitializedException;
import com.yashshree.intuit.demo.Intuit.demo.services.LeaderBoardService;

import com.yashshree.intuit.demo.Intuit.demo.entity.LeaderBoard;
import com.yashshree.intuit.demo.Intuit.demo.entity.ScoreBoard;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class LeaderBoardControllerTest {

    @Mock
    private LeaderBoardService leaderBoardService;

    @InjectMocks
    private LeaderBoardController leaderBoardController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateBoardSuccess() throws LeaderboardNotInitializedException, CacheInitializationException {
        LeaderBoard leaderBoard = new LeaderBoard();
        leaderBoard.setLeaderBoardSize(10);

        doNothing().when(leaderBoardService).createBoard(leaderBoard.getLeaderBoardSize());

        leaderBoardController.createBoard(leaderBoard);

        verify(leaderBoardService, times(1)).createBoard(leaderBoard.getLeaderBoardSize());
    }

    @Test
    public void testCreateBoardFailure() throws LeaderboardNotInitializedException, CacheInitializationException {
        LeaderBoard leaderBoard = new LeaderBoard();
        leaderBoard.setLeaderBoardSize(10);

        doThrow(new RuntimeException("Creation failed")).when(leaderBoardService).createBoard(leaderBoard.getLeaderBoardSize());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            leaderBoardController.createBoard(leaderBoard);
        });

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatusCode());
        assertEquals("Creation failed", exception.getReason());
    }

    @Test
    public void testGetTopScorersSuccess() throws Exception {
        List<ScoreBoard> topScorers = new ArrayList<>();
        when(leaderBoardService.getTopNPlayers()).thenReturn(topScorers);

        List<ScoreBoard> result = leaderBoardController.getTopScorers();

        assertEquals(topScorers, result);
        verify(leaderBoardService, times(1)).getTopNPlayers();
    }

    @Test
    public void testGetTopScorersLeaderboardNotInitialized() throws Exception {
        doThrow(new LeaderboardNotInitializedException("Leaderboard not initialized")).when(leaderBoardService).getTopNPlayers();

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            leaderBoardController.getTopScorers();
        });

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertEquals("Please register/create LeaderBoard first", exception.getReason());
    }

    @Test
    public void testGetTopScorersFailure() throws Exception {
        doThrow(new RuntimeException("Failed to get top scores")).when(leaderBoardService).getTopNPlayers();

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            leaderBoardController.getTopScorers();
        });

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatusCode());
        assertEquals("Failed to get top scores", exception.getReason());
    }
}