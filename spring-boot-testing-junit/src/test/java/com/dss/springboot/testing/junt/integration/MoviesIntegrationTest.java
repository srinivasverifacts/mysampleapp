package com.dss.springboot.testing.junt.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.client.RestTemplate;
import org.testcontainers.shaded.org.checkerframework.checker.nullness.qual.AssertNonNullIfNonNull;

import com.dss.springboot.testing.junt.model.Movie;
import com.dss.springboot.testing.junt.repository.MovieRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MoviesIntegrationTest {

	@LocalServerPort
	private int port;
	
	private String baseUrl = "http://localhost";
	
	private static RestTemplate  restTemplate;
	
	@Autowired
	private  MovieRepository movieRepository;
	
	
	@BeforeAll
	public static void init() {
		restTemplate = new RestTemplate();
	}
	
	private Movie avatarMovie;
	private Movie titanicMovie;
	
	@BeforeEach
	public void beforSetup() {
		baseUrl = baseUrl + ":" +port+"/movies";
		
		avatarMovie = new Movie();
		avatarMovie.setName("Avatar");
		avatarMovie.setGenera("Action");
		avatarMovie.setReleaseDate(LocalDate.of(2000, Month.APRIL, 21));
		
		titanicMovie = new Movie();
		titanicMovie.setName("Titanic");
		titanicMovie.setGenera("Romance");
		titanicMovie.setReleaseDate(LocalDate.of(1998, Month.MAY, 19));
		
		movieRepository.save(avatarMovie);
		movieRepository.save(titanicMovie);
	}
	
	@AfterEach
	public void afterSetup() {
		movieRepository.deleteAll();
	}
	
	@Test
	void shouldCreateMovieTest() {
  Movie avatarMovie = new Movie();
		avatarMovie.setId(1L);
		avatarMovie.setName("Avatar");
		avatarMovie.setGenera("Action");
		avatarMovie.setReleaseDate(LocalDate.of(2000, Month.APRIL, 21));
		
	Movie newMovie=	restTemplate.postForObject(baseUrl, avatarMovie, Movie.class);
	
	assertNotNull(avatarMovie);
	assertThat(newMovie.getId()).isNotNull();
	}
	
	
	
	@Test 
	void shouldFetchMoviesTest(){
		/*
		 * Movie avatarMovie = new Movie(); avatarMovie.setName("Avatar");
		 * avatarMovie.setGenera("Action");
		 * avatarMovie.setReleaseDate(LocalDate.of(2000, Month.APRIL, 21));
		 * 
		 * Movie titanicMovie = new Movie(); titanicMovie.setName("Titanic");
		 * titanicMovie.setGenera("Romance");
		 * titanicMovie.setReleaseDate(LocalDate.of(1998, Month.MAY, 19));
		 */
		
		restTemplate.postForObject(baseUrl, avatarMovie, Movie.class);
		restTemplate.postForObject(baseUrl, titanicMovie, Movie.class);
		
	List<Movie> list=restTemplate.getForObject(baseUrl, List.class);
	
	assertThat(list.size()).isEqualTo(2);
		
	}
	
	@Test
	void shouldFetchOneMoviesTest() {
		/*
		 * Movie avatarMovie = new Movie(); avatarMovie.setName("Avatar");
		 * avatarMovie.setGenera("Action");
		 * avatarMovie.setReleaseDate(LocalDate.of(2000, Month.APRIL, 21));
		 * 
		 * Movie titanicMovie = new Movie(); titanicMovie.setName("Titanic");
		 * titanicMovie.setGenera("Romance");
		 * titanicMovie.setReleaseDate(LocalDate.of(1998, Month.MAY, 19));
		 */
		
		avatarMovie=restTemplate.postForObject(baseUrl, avatarMovie, Movie.class);
		titanicMovie=restTemplate.postForObject(baseUrl, titanicMovie, Movie.class);
		
	Movie existingMovie=restTemplate.getForObject(baseUrl+"/"+avatarMovie.getId(), Movie.class);
	
	assertNotNull(existingMovie);
	assertEquals("Avatar", existingMovie.getName());
		
		
	}
	
	
	@Test
	void shouldDeleteMovieTest() {
		/*
		 * Movie avatarMovie = new Movie(); avatarMovie.setName("Avatar");
		 * avatarMovie.setGenera("Action");
		 * avatarMovie.setReleaseDate(LocalDate.of(2000, Month.APRIL, 21));
		 * 
		 * Movie titanicMovie = new Movie(); titanicMovie.setName("Titanic");
		 * titanicMovie.setGenera("Romance");
		 * titanicMovie.setReleaseDate(LocalDate.of(1998, Month.MAY, 19));
		 */
		avatarMovie=restTemplate.postForObject(baseUrl, avatarMovie, Movie.class);
		titanicMovie=restTemplate.postForObject(baseUrl, titanicMovie, Movie.class);
		
		restTemplate.delete(baseUrl+"/"+avatarMovie.getId());
		 int count =movieRepository.findAll().size();
		 
		 assertEquals(1, count);
		
	}
	
	@Test
	void shouldUpdateMovieTest() {
		/*
		 * Movie avatarMovie = new Movie(); avatarMovie.setName("Avatar");
		 * avatarMovie.setGenera("Action");
		 * avatarMovie.setReleaseDate(LocalDate.of(2000, Month.APRIL, 21));
		 * 
		 * Movie titanicMovie = new Movie(); titanicMovie.setName("Titanic");
		 * titanicMovie.setGenera("Romance");
		 * titanicMovie.setReleaseDate(LocalDate.of(1998, Month.MAY, 19));
		 */
		avatarMovie=restTemplate.postForObject(baseUrl, avatarMovie, Movie.class);
		titanicMovie=restTemplate.postForObject(baseUrl, titanicMovie, Movie.class);
		
		avatarMovie.setGenera("Fantacy");
		
		restTemplate.put(baseUrl+"/{id}", avatarMovie,avatarMovie.getId());
		
		Movie existingMovie = restTemplate.getForObject(baseUrl+"/"+avatarMovie.getId(), Movie.class);
		
		assertNotNull(existingMovie);
		assertEquals("Fantacy", existingMovie.getGenera());
		
	}
	
}
