package com.github.aqd.desafios.spring.batch.web.api;

import static org.junit.Assert.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.github.aqd.desafios.spring.batch.Main;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author antonio.duarte
 *
 */
@Slf4j
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Main.class)
@PropertySource("classpath:application.yml")
public class MovieResourceTest {

	private static final String BASE_URL = "/movies/winners/";
	
	@Autowired
	MockMvc mockMvc;
	
	@InjectMocks 
	MovieResource movieResource;
	
	@Test
	public void getSmallestTest() {
		try {
			mockMvc
			.perform(get(BASE_URL + "smallest")
					.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content()
					.json("[{"
							+ "\"producer\":\"Joel Silver\","
							+ "\"previousWin\":1990,"
							+ "\"followingWin\":1991,"
							+ "\"interval\":1"
						+ "}]"));
		} catch (Exception e) {
			log.error(e.toString());
			fail(e.getMessage());
		}
	}
	
	@Test
	public void getMaxIntevalTest() {
		try {
			mockMvc
			.perform(get(BASE_URL + "greater")
					.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content()
					.json("[{"
							+ "\"producer\":\"Matthew Vaughn\","
							+ "\"previousWin\":2002,"
							+ "\"followingWin\":2028,"
							+ "\"interval\":26"
						+ "}]"));
		} catch (Exception e) {
			log.error(e.toString());
			fail(e.getMessage());
		}
	}
	
	@Test
	public void getWinniersTest() {
		try {
			mockMvc
			.perform(get(BASE_URL + "smallest-and-greater-interval")
					.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content()
					.json("{"
							+ "\"min\":["
								+ "{"
									+ "\"producer\":\"Joel Silver\","
									+ "\"previousWin\":1990,"
									+ "\"followingWin\":1991,"
									+ "\"interval\":1"
								+ "}"
							+ "],"
							+ "\"max\":["
								+ "{"
									+ "\"producer\":\"Matthew Vaughn\","
									+ "\"previousWin\":2002,"
									+ "\"followingWin\":2028,"
									+ "\"interval\":26"
								+ "}"
							+ "]"
						+ "}"));
		} catch (Exception e) {
			log.error(e.toString());
			fail(e.getMessage());
		}
	}
	
}
