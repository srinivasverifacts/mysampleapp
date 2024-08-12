package com.dss.springboot.testing.junt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.dss.springboot.testing.junt.model.Movie;
import com.dss.springboot.testing.junt.service.MovieService;

@RestController
@RequestMapping("/movies")
public class MovieController {

	@Autowired
	private MovieService movieService;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Movie create(@RequestBody Movie movie) {
		return movieService.save(movie);
	}
	
	  @GetMapping
	  @ResponseStatus(HttpStatus.OK) 
	  public List<Movie> read() {
		  	return movieService.getAllMovies();
	    }
	 
	  @GetMapping("/{id}")
	  @ResponseStatus(HttpStatus.OK)
	  public Movie read(@PathVariable Long id ) {
	  return movieService.getMovieById(id);
	  
	 }
	  
	  @PutMapping("/{id}")
	  @ResponseStatus(HttpStatus.OK) 
	  public Movie updateMovie(@PathVariable Long
	  id,@RequestBody Movie movie) {
	 
	  return movieService.updateMovie(movie, id);
	  
	  }
	  
	 @DeleteMapping("/{id}")
	 @ResponseStatus(HttpStatus.NO_CONTENT) 
	 public void delete(@PathVariable Long id) {
	  movieService.deleteMovie(id);
	  
	  }
	
public void test(){

//logic here;
} 
	
	
	
}
