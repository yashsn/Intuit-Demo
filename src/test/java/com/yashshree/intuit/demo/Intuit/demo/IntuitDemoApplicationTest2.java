package com.yashshree.intuit.demo.Intuit.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yashshree.intuit.demo.Intuit.demo.controller.LeaderBoardController;
import com.yashshree.intuit.demo.Intuit.demo.entity.LeaderBoard;
import com.yashshree.intuit.demo.Intuit.demo.repository.ScoreBoardRepository;
import com.yashshree.intuit.demo.Intuit.demo.services.LeaderBoardService;
import com.yashshree.intuit.demo.Intuit.demo.services.LeaderBoardServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@ExtendWith(MockitoExtension.class)
@WebMvcTest(value = LeaderBoardController.class)
//@AutoConfigureMockMvc
@RunWith(MockitoJUnitRunner.class)
class IntuitDemoApplicationTest2 {

	@Mock
	private ScoreBoardRepository repository;
	@InjectMocks
	private LeaderBoardServiceImpl leaderBoard;

	@Autowired
	private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
    }

    @Test
	public void whenFileUploaded_thenVerifyStatus()
			throws Exception {

		LeaderBoard lb=new LeaderBoard(23,"hi",5);
		ObjectMapper objectMapper = new ObjectMapper();

		mockMvc.perform(MockMvcRequestBuilders.post("/createBoard")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(lb))
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
}