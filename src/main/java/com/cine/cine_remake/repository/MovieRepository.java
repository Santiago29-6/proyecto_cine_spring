package com.cine.cine_remake.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cine.cine_remake.model.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long>{

}
