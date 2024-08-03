package com.dss.springboot.testing.junt.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;

import org.hibernate.boot.model.relational.InitCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.dss.springboot.testing.junt.model.Movie;

@DataJpaTest
public class MovieRepositoryTest {

	@Autowired
	private MovieRepository movieRepository;
	
	private Movie avatarMovie ;
	private Movie titanicMovie;
	
	@BeforeEach
	void init(){
		avatarMovie = new Movie();
		avatarMovie.setName("Avatar");
		avatarMovie.setGenera("Action");
		avatarMovie.setReleaseDate(LocalDate.of(2000, Month.APRIL, 21));
		
		titanicMovie = new Movie();
		titanicMovie.setName("Titanic");
		titanicMovie.setGenera("Romance");
		titanicMovie.setReleaseDate(LocalDate.of(1998, Month.MAY, 19));
		
		
	}
	
	
	@Test
	@DisplayName("It should save the movie to the database")
	void save() {
		//Arrange
		//Movie avatarMovie = new Movie();
		//avatarMovie.setName("Avatar");
		//avatarMovie.setGenera("Action");
		//avatarMovie.setReleaseDate(LocalDate.of(2000, Month.APRIL, 21));
		
		//Act
		Movie newMovie = movieRepository.save(avatarMovie);
		
		//Assert
		assertNotNull(newMovie);
		assertThat(newMovie.getId()).isNotEqualTo(null);
	}
	
	@Test
	@DisplayName("It should return the movie list with size of 2")
	void getAllMovies() {
		//Movie avatarMovie = new Movie();
		//avatarMovie.setName("Avatar");
		//avatarMovie.setGenera("Action");
		//avatarMovie.setReleaseDate(LocalDate.of(2000, Month.APRIL, 21));
		
		movieRepository.save(avatarMovie);
		
		
		//Movie titanicMovie = new Movie();
		//titanicMovie.setName("Titanic");
		//titanicMovie.setGenera("Romance");
		//titanicMovie.setReleaseDate(LocalDate.of(1998, Month.MAY, 19));
		
		movieRepository.save(titanicMovie);
		
		
		List<Movie> list = movieRepository.findAll();
		assertNotNull(list);
		assertThat(list).isNotNull();
		assertEquals(2, list.size());
		
		
	}
	
	@Test
	@DisplayName("It Should return the movie by its id")
	void getMovieById() {
		//Movie avatarMovie = new Movie();
		//avatarMovie.setName("Avatar");
		//avatarMovie.setGenera("Action");
		//avatarMovie.setReleaseDate(LocalDate.of(2000, Month.APRIL, 21));
		
		movieRepository.save(avatarMovie);
		
		Movie existingMovie = movieRepository.findById(avatarMovie.getId()).get();
		
		assertNotNull(existingMovie);
		assertEquals("Action", existingMovie.getGenera());
		assertThat(avatarMovie.getReleaseDate()).isBefore(LocalDate.of(2000, Month.APRIL, 22));
		
	}
	
	@Test
	@DisplayName("It should update the movie with genera Fantacy")
	void updateMovie() {
		//Movie avatarMovie = new Movie();
		//avatarMovie.setName("Avatar");
		//avatarMovie.setGenera("Action");
		//avatarMovie.setReleaseDate(LocalDate.of(2000, Month.APRIL, 21));
		movieRepository.save(avatarMovie);
		Movie existingMovie = movieRepository.findById(avatarMovie.getId()).get();
		
		existingMovie.setGenera("Fantacy");
		Movie newMovie = movieRepository.save(existingMovie);
		
		assertEquals("Fantacy", newMovie.getGenera());
		assertEquals("Avatar", newMovie.getName());
		
		
	}
	
	@Test
	@DisplayName("It Should delete the existing movie")
	void deletMovie() {
		//Movie avatarMovie = new Movie();
		//avatarMovie.setName("Avatar");
		//avatarMovie.setGenera("Action");
		//avatarMovie.setReleaseDate(LocalDate.of(2000, Month.APRIL, 21));
		movieRepository.save(avatarMovie);
		Long id = avatarMovie.getId();
		
		//Movie titanicMovie = new Movie();
		//titanicMovie.setName("Titanic");
		//titanicMovie.setGenera("Romance");
		//titanicMovie.setReleaseDate(LocalDate.of(1998, Month.MAY, 19));
		movieRepository.save(titanicMovie);
		
		movieRepository.delete(avatarMovie);
		Optional<Movie> existingMovie = movieRepository.findById(id);
		List<Movie> list = movieRepository.findAll();
		
		assertEquals(1, list.size());
		assertThat(existingMovie).isEmpty();
		
		
	}
	
	@Test
	@DisplayName("It Should return the movies list with Genera")
	void getMoviesByGenera() {
		//Movie avatarMovie = new Movie();
		//avatarMovie.setName("Avatar");
		//avatarMovie.setGenera("Action");
		//avatarMovie.setReleaseDate(LocalDate.of(2000, Month.APRIL, 21));
		movieRepository.save(avatarMovie);
			
		//Movie titanicMovie = new Movie();
		//titanicMovie.setName("Titanic");
		//titanicMovie.setGenera("Romance");
		//titanicMovie.setReleaseDate(LocalDate.of(1998, Month.MAY, 19));
		movieRepository.save(titanicMovie);
		
		
		List<Movie> list = movieRepository.findByGenera("Romance");
		
		assertNotNull(list);
		assertEquals(1, list.size());
		assertThat(list.size()).isEqualTo(1);
		
	}
	
	
}
