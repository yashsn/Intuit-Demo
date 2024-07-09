package com.yashshree.intuit.demo.Intuit.demo.services;

//import static org.junit.jupiter.api.Assertions.*;
//
//class CacheServiceImplTest {
//
//}
//package com.yashshree.intuit.demo.Intuit.demo.services;

import com.yashshree.intuit.demo.Intuit.demo.entity.ScoreBoard;
import com.yashshree.intuit.demo.Intuit.demo.exceptions.CacheInitializationException;
import com.yashshree.intuit.demo.Intuit.demo.exceptions.CacheUpdateFailureException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class CacheServiceImplTest {

    @InjectMocks
    private CacheServiceImpl cacheService;
    private int TEST_TOP_N=2;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        cacheService = new CacheServiceImpl();
    }

    @Test
    public void testInitializeSuccess() throws CacheInitializationException {
        List<ScoreBoard> data = new ArrayList<>();
        data.add(new ScoreBoard("user1", 1,100));
        data.add(new ScoreBoard("user2", 2,200));
        data.add(new ScoreBoard("user3", 3,300));
        data.add(new ScoreBoard("user4", 4,30));

        cacheService.initialize(TEST_TOP_N, data);

        assertEquals(TEST_TOP_N, cacheService.getTopNplayers().size());
        assertTrue(cacheService.usernameToScore.containsKey("user3"));
        assertTrue(cacheService.usernameToScore.containsKey("user2"));
    }

    @Test
    public void testInitializeFailure() {
        List<ScoreBoard> data = null;

        CacheInitializationException exception = assertThrows(CacheInitializationException.class, () -> {
            cacheService.initialize(TEST_TOP_N, data);
        });

        assertEquals("Failed to initialize cache", exception.getMessage());
    }

    @Test
    public void testAddToCacheNewEntry() throws CacheUpdateFailureException, CacheInitializationException {
        List<ScoreBoard> data = new ArrayList<>();
        cacheService.initialize(TEST_TOP_N, data);

        ScoreBoard score = new ScoreBoard("user1", 1,100);
        cacheService.addtoCache(score);

        assertTrue(cacheService.usernameToScore.containsKey("user1"));
        assertEquals(1, cacheService.getTopNplayers().size());
    }

    @Test
    public void testAddToCacheUpdateExistingEntry() throws CacheUpdateFailureException, CacheInitializationException {
        List<ScoreBoard> data = new ArrayList<>();
        cacheService.initialize(TEST_TOP_N, data);

        ScoreBoard score1 = new ScoreBoard("user1", 1,100);
        cacheService.addtoCache(score1);

        ScoreBoard score2 = new ScoreBoard("user1", 2,200);
        cacheService.addtoCache(score2);

        assertTrue(cacheService.usernameToScore.containsKey("user1"));
        assertEquals(1, cacheService.getTopNplayers().size());
        assertEquals(200, cacheService.usernameToScore.get("user1").getScore());
    }

    @Test
    public void testAddToCacheFailure() throws CacheInitializationException {
        List<ScoreBoard> data = new ArrayList<>();
        cacheService.initialize(TEST_TOP_N, data);

        ScoreBoard score = null;

        CacheUpdateFailureException exception = assertThrows(CacheUpdateFailureException.class, () -> {
            cacheService.addtoCache(score);
        });

        assertNotNull(exception.getMessage());
    }

    @Test
    public void testGetTopNPlayers() throws CacheInitializationException {
        List<ScoreBoard> data = new ArrayList<>();
        data.add(new ScoreBoard("user1", 1,100));
        data.add(new ScoreBoard("user2", 2,200));
        cacheService.initialize(TEST_TOP_N, data);

        List<ScoreBoard> topPlayers = cacheService.getTopNplayers();

        assertEquals(TEST_TOP_N, topPlayers.size());
        assertEquals("user2", topPlayers.get(0).getUsername());
        assertEquals(200, topPlayers.get(0).getScore());
        assertEquals("user1", topPlayers.get(1).getUsername());
        assertEquals(100, topPlayers.get(1).getScore());
    }
}
