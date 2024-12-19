package com.cine.cine_remake.services.movie;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.cine.cine_remake.model.Movie;

@Service
public interface MovieService {

    List<Movie> findAllMovies();

    Optional<Movie> findMovieById(Long id_movie);

    Movie saveMovie(Movie movie);

    void deleteMovie(Long id_movie);
    
}
