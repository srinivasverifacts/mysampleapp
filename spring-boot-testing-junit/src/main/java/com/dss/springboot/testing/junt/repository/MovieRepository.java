package com.dss.springboot.testing.junt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dss.springboot.testing.junt.model.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

	
	List<Movie> findByGenera(String genera);
}
