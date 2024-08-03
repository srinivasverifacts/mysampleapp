package com.dss.springboot.testing.junt.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.aspectj.weaver.NewMemberClassTypeMunger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.dss.springboot.testing.junt.model.Movie;
import com.dss.springboot.testing.junt.repository.MovieRepository;

@ExtendWith(MockitoExtension.class)
public class MovieServiceTest {
	
	@InjectMocks
	private MovieService movieService;
	
	@Mock
	private MovieRepository movieRepository;
	
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
	@DisplayName("Should save the save the movie Object to database")
	void save() {
		/*
		 * Movie avatarMovie = new Movie(); avatarMovie.setId(1L);
		 * avatarMovie.setName("Avatar"); avatarMovie.setGenera("Action");
		 * avatarMovie.setReleaseDate(LocalDate.of(2000, Month.APRIL, 20));
		 */
		
		when(movieRepository.save(any(Movie.class))).thenReturn(avatarMovie);
		
		Movie newMovie = movieService.save(avatarMovie);
		
		assertNotNull(avatarMovie);
		assertThat(newMovie.getName()).isEqualTo("Avatar");
		
	}
	
	@Test
	@DisplayName("Should return list of movies with size 2")
	void getMovies() {
		/*
		 * Movie avatarMovie = new Movie(); avatarMovie.setId(1L);
		 * avatarMovie.setName("Avatar"); avatarMovie.setGenera("Action");
		 * avatarMovie.setReleaseDate(LocalDate.of(2000, Month.APRIL, 21));
		 * 
		 * Movie titanicMovie = new Movie(); titanicMovie.setId(2L);
		 * titanicMovie.setName("Titanic"); titanicMovie.setGenera("Romance");
		 * titanicMovie.setReleaseDate(LocalDate.of(1998, Month.MAY, 19));
		 */
		
		List<Movie> list = new ArrayList<>();
		list.add(titanicMovie);
		list.add(avatarMovie);
		
		when(movieRepository.findAll()).thenReturn(list);
		
		List<Movie> movies = movieService.getAllMovies();
		
		assertNotNull(movies);
		assertEquals(2, movies.size());
	}
	
	@Test
	@DisplayName("Should return the Movie Object")
	void getMovieById() {
		/*
		 * Movie avatarMovie = new Movie(); avatarMovie.setId(1L);
		 * avatarMovie.setName("Avatar"); avatarMovie.setGenera("Action");
		 * avatarMovie.setReleaseDate(LocalDate.of(2000, Month.APRIL, 21));
		 */
		
		when(movieRepository.findById(anyLong())).thenReturn(Optional.of(avatarMovie));
		
		Movie existingMovie = movieService.getMovieById(1L);
		
		assertNotNull(existingMovie);
		assertThat(existingMovie.getId()).isEqualTo(1L);
	}
	
	@Test
	@DisplayName("Should throw Exception")
	void getMovieByIdForException() {
		/*
		 * Movie avatarMovie = new Movie(); avatarMovie.setId(1L);
		 * avatarMovie.setName("Avatar"); avatarMovie.setGenera("Action");
		 * avatarMovie.setReleaseDate(LocalDate.of(2000, Month.APRIL, 21));
		 */
		
		when(movieRepository.findById(1L)).thenReturn(Optional.of(avatarMovie));
		
		assertThrows(RuntimeException.class, ()->{
			movieService.getMovieById(2L);
		});
		
	}
	
	@Test
	@DisplayName("Should update the movie into database")
	void updateMovie() {
		/*
		 * Movie avatarMovie = new Movie(); avatarMovie.setId(1L);
		 * avatarMovie.setName("Avatar"); avatarMovie.setGenera("Action");
		 * avatarMovie.setReleaseDate(LocalDate.of(2000, Month.APRIL, 21));
		 */
		
		when(movieRepository.findById(anyLong())).thenReturn(Optional.of(avatarMovie));
		when(movieRepository.save(any(Movie.class))).thenReturn(avatarMovie);
		avatarMovie.setGenera("Fantacy");
		
		Movie updatedMovie =movieService.updateMovie(avatarMovie, 1L);
		
		
		assertNotNull(updatedMovie);
		assertEquals("Fantacy", updatedMovie.getGenera());
		
		
	}
	
	@Test
	@DisplayName("should delete the movie from database")
	void deleteMovie() {
		/*
		 * Movie avatarMovie = new Movie(); avatarMovie.setId(1L);
		 * avatarMovie.setName("Avatar"); avatarMovie.setGenera("Action");
		 * avatarMovie.setReleaseDate(LocalDate.of(2000, Month.APRIL, 21));
		 */
		
		when(movieRepository.findById(anyLong())).thenReturn(Optional.of(avatarMovie));
		doNothing().when(movieRepository).delete(any(Movie.class));
		
		movieService.deleteMovie(1L);
		
		verify(movieRepository, times(1)).delete(avatarMovie);
		
	}

}
