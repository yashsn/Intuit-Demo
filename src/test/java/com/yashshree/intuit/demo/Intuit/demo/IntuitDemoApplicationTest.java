package com.yashshree.intuit.demo.Intuit.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class IntuitDemoApplicationTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void contextLoads() {
        // The contextLoads test is used to verify if the application context loads successfully.
        assertNotNull(applicationContext, "The application context should have loaded.");
    }

    @Test
    public void testMain() {
        // We can call the main method to ensure it runs without throwing any exceptions.
        IntuitDemoApplication.main(new String[]{});
    }
}

