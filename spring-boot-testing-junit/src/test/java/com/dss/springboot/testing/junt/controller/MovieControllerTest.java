package com.dss.springboot.testing.junt.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.print.attribute.standard.Media;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.dss.springboot.testing.junt.model.Movie;
import com.dss.springboot.testing.junt.service.MovieService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@WebMvcTest
public class MovieControllerTest {

	@MockBean
	private MovieService movieService;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	
	private Movie avatarMovie ;
	private Movie titanicMovie;
	
	@BeforeEach
	void init(){
		avatarMovie = new Movie();
		avatarMovie.setId(1L);
		avatarMovie.setName("Avatar");
		avatarMovie.setGenera("Action");
		avatarMovie.setReleaseDate(LocalDate.of(2000, Month.APRIL, 21));
		
		titanicMovie = new Movie();
		titanicMovie.setId(2L);
		titanicMovie.setName("Titanic");
		titanicMovie.setGenera("Romance");
		titanicMovie.setReleaseDate(LocalDate.of(1998, Month.MAY, 19));
		
		
	}
	
		
	@Test
	void  create() throws Exception {
		/*
		 * Movie avatarMovie = new Movie(); avatarMovie.setId(1L);
		 * avatarMovie.setName("Avatar"); avatarMovie.setGenera("Action");
		 * avatarMovie.setReleaseDate(LocalDate.of(2000, Month.APRIL, 21));
		 */
		  
		  when(movieService.save(any(Movie.class))).thenReturn(avatarMovie);
		  
		  this.mockMvc.perform(post("/movies")
				  .contentType(MediaType.APPLICATION_JSON)
				  .content(objectMapper.writeValueAsString(avatarMovie)))
		  			.andExpect(status().isCreated())
		  			.andExpect(jsonPath("$.name", is(avatarMovie.getName())))
		  			.andExpect(jsonPath("$.genera", is(avatarMovie.getGenera())))
		  			.andExpect(jsonPath("$.releaseDate", is(avatarMovie.getReleaseDate().toString())));
				  
		}
	
	@Test
	void ShouldFetchAllMovies() throws Exception {
//		Movie avatarMovie = new Movie();
//		avatarMovie.setName("Avatar");
//		avatarMovie.setGenera("Action");
//		avatarMovie.setReleaseDate(LocalDate.of(2000, Month.APRIL, 21));
//		
//		Movie titanicMovie = new Movie();
//		titanicMovie.setName("Titanic");
//		titanicMovie.setGenera("Romance");
//		titanicMovie.setReleaseDate(LocalDate.of(1998, Month.MAY, 19));
		
		List<Movie> list = new ArrayList<>();
		list.add(titanicMovie);
		list.add(avatarMovie);
		
		when(movieService.getAllMovies()).thenReturn(list);
		
		this.mockMvc.perform(get("/movies"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.size()", is(list.size())));
		
	}
	
	
	@Test
	void ShouldFetchMovieById() throws Exception {
		/*
		 * Movie avatarMovie = new Movie(); avatarMovie.setId(1L);
		 * avatarMovie.setName("Avatar"); avatarMovie.setGenera("Action");
		 * avatarMovie.setReleaseDate(LocalDate.of(2000, Month.APRIL, 21));
		 */
		
		when(movieService.getMovieById(anyLong())).thenReturn(avatarMovie);
		
		this.mockMvc.perform(get("/movies/{id}",1L))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.name", is(avatarMovie.getName())))
		.andExpect(jsonPath("$.genera", is(avatarMovie.getGenera())))
		.andExpect(jsonPath("$.releaseDate", is(avatarMovie.getReleaseDate().toString())));
		
	}
	
	  @Test 
	  void ShouldUpdateMovieById() throws Exception { 
			/*
			 * Movie avatarMovie = new Movie(); avatarMovie.setId(1L);
			 * avatarMovie.setName("Avatar"); avatarMovie.setGenera("Action");
			 * avatarMovie.setReleaseDate(LocalDate.of(2000, Month.APRIL, 21));
			 */
	  
	 when(movieService.updateMovie(any(Movie.class), anyLong())).thenReturn(avatarMovie);
	 
	  this.mockMvc.perform(put("/movies/{id}",1L)
			  .contentType(MediaType.APPLICATION_JSON)
			  .content(objectMapper.writeValueAsString(avatarMovie))) 
	  		  .andExpect(status().isOk())
	  		  .andExpect(jsonPath("$.name", is(avatarMovie.getName())))
	  		  .andExpect(jsonPath("$.genera", is(avatarMovie.getGenera())));
	  
	  }
	 
	
	@Test
	void shouldDeleteMovieById() throws Exception {
		/*
		 * Movie avatarMovie = new Movie(); avatarMovie.setId(1L);
		 * avatarMovie.setName("Avatar"); avatarMovie.setGenera("Action");
		 * avatarMovie.setReleaseDate(LocalDate.of(2000, Month.APRIL, 21));
		 */
		
		doNothing().when(movieService).deleteMovie(anyLong());
		
		this.mockMvc.perform(delete("/movies/{id}",1L))
		.andExpect(status().isNoContent());
		
		
	}
	
}
