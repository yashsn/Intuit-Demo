package com.yashshree.intuit.demo.Intuit.demo;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.yashshree.intuit.demo.Intuit.demo.controller.GameController;
//import com.yashshree.intuit.demo.Intuit.demo.entity.LeaderBoard;
//import com.yashshree.intuit.demo.Intuit.demo.repository.ScoreBoardRepository;
//import com.yashshree.intuit.demo.Intuit.demo.services.*;
//import org.aspectj.lang.annotation.Before;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//
////@WebMvcTest(LeaderBoardController.class)
////@AutoConfigureMockMvc
//@WebMvcTest
//@ContextConfiguration(classes=IntuitDemoApplication.class)
//@ExtendWith(SpringExtension.class)
//class IntuitDemoApplicationTests {
//	@Autowired
//	private MockMvc mockMvc;
//	@Mock
//	ScoreIngestionSevice scoreIngestionSevice;
//	@Mock
//	ScoreToLBoard scoreIngestor;
//	@Mock
//	ScoreToDB scoreToDB;
//	@Mock
//	private LeaderBoardService leaderBoard;
//
//	@InjectMocks
//	private LeaderBoardServiceImpl leaderBoardServiceImpl;
//	@InjectMocks
//	private  ScoreIngestionImpl scoreIngestionSI;
//	@InjectMocks
//	private GameController gameController;
//
//	@Before("")
//    public void setUp(){
//		MockitoAnnotations.openMocks(this);
//	}
//	@Test
//	void contextLoads() {
//	}
//	@Test
//	public void testCreateBoard(){
//
//	}
////	@Test
////	public void whenFileUploaded_thenVerifyStatus()
////			throws Exception {
////
////		LeaderBoard lb=new LeaderBoard(23,"hi",5);
////		ObjectMapper objectMapper = new ObjectMapper();
////
////		mockMvc.perform(MockMvcRequestBuilders.post("/createBoard")
////						.contentType(MediaType.APPLICATION_JSON)
////						.content(objectMapper.writeValueAsString(lb))
////						.accept(MediaType.APPLICATION_JSON))
////				.andExpect(status().isOk());
//////		mockMvc.perform( MockMvcRequestBuilders
//////						.post("/createBoard")
//////						.content(objectMapper.writeValueAsString(lb))
//////						.contentType(MediaType.APPLICATION_JSON)
//////						.accept(MediaType.APPLICATION_JSON))
//////				.andExpect(status().isCreated())
//////				.andExpect(MockMvcResultMatchers.jsonPath("").exists());
////	}
//
//}


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
		IntuitDemoApplication.main(new String[] {});
	}
}

