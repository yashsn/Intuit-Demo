package com.yashshree.intuit.demo.Intuit.demo.controller;

import com.yashshree.intuit.demo.Intuit.demo.entity.ScoreBoard;
import com.yashshree.intuit.demo.Intuit.demo.exceptions.DatabaseStorageException;
import com.yashshree.intuit.demo.Intuit.demo.services.ScoreIngestionSevice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

public class GameControllerTest {

    @Mock
    private ScoreIngestionSevice scoreIngestor;

    @InjectMocks
    private GameController gameController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testUpdateScoreSuccess() throws Exception {
        ScoreBoard currentScore = new ScoreBoard("user1", 1,100);

        gameController.updateScore(currentScore);

        verify(scoreIngestor).publish(currentScore);
    }

    @Test
    public void testUpdateScoreFailure() throws Exception {
        ScoreBoard currentScore = new ScoreBoard("user1", 1,100);

        doThrow(new DatabaseStorageException("Failed to add to DB")).when(scoreIngestor).publish(currentScore);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            gameController.updateScore(currentScore);
        });

        verify(scoreIngestor).publish(currentScore);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatusCode());
        assertEquals("Failed to add to DB", exception.getReason());
        assertNotNull(exception.getMessage());
    }
}
