package com.learn.movie.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.learn.movie.service.model.Movie;
import com.learn.movie.service.repository.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MovieServiceApplicationTests  {

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	MockMvc mockMvc;

	@Autowired
	MovieRepository movieRepository;

	@BeforeEach
	void cleanUp(){
		movieRepository.deleteAllInBatch();
	}

	@Test
	void for_createMovie() throws Exception{
		Movie movie =new Movie();
		movie.setName("BB");
		movie.setDirector("Sambu");
		movie.setActors(List.of("prabha","bujji","brami"));

		var response = mockMvc.perform(post("/movies/create")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(movie)));

		response.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(notNullValue())))
				.andExpect(jsonPath("$.name", is(movie.getName())))
				.andExpect(jsonPath("$.director", is(movie.getDirector())))
				.andExpect(jsonPath("$.actors", is(movie.getActors())));
	}

	@Test
	void for_Fetch() throws Exception{

		Movie movie =new Movie();
		movie.setName("BB");
		movie.setDirector("Sambu");
		movie.setActors(List.of("prabha","bujji","brami"));

		Movie movie1 = movieRepository.save(movie);

		var response = mockMvc.perform(get("/movies/"+movie1.getId())
				 				.contentType(MediaType.APPLICATION_JSON));
		response.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(movie1.getId().intValue())))
				.andExpect(jsonPath("$.name", is(movie.getName())))
				.andExpect(jsonPath("$.director", is(movie.getDirector())))
				.andExpect(jsonPath("$.actors", is(movie.getActors())));
	}

	@Test
	void for_update() throws  Exception{
		Movie movie =new Movie();
		movie.setName("BB");
		movie.setDirector("Sambu");
		movie.setActors(List.of("prabha","bujji","brami"));

		Movie movie1 = movieRepository.save(movie);
		movie1.setActors(List.of("prabha","bujji","brami","desha"));

		var response = mockMvc.perform(put("/movies/update")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(movie1)));

		response.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(movie1.getId().intValue())))
				.andExpect(jsonPath("$.name", is(movie1.getName())))
				.andExpect(jsonPath("$.director", is(movie1.getDirector())))
				.andExpect(jsonPath("$.actors", is(movie1.getActors())));
	}

	@Test
	void for_delete() throws Exception {
		Movie movie =new Movie();
		movie.setName("BB");
		movie.setDirector("Sambu");
		movie.setActors(List.of("prabha","bujji","brami"));
		Movie movie1 = movieRepository.save(movie);

		var response = mockMvc.perform(delete("/movies/"+movie1.getId())
				.contentType(MediaType.APPLICATION_JSON));

		response.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$" ,is("Movie deleted")));

		assertFalse(movieRepository.findById(movie1.getId()).isPresent());
	}

}
