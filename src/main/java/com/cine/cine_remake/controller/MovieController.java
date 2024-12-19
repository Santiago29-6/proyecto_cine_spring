package com.cine.cine_remake.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cine.cine_remake.model.Movie;
import com.cine.cine_remake.services.movie.MovieService;

@RestController
@RequestMapping("api/v1/movie")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping("/all")
    public ResponseEntity<List<Movie>> getAllMovies() {
        return ResponseEntity.ok(movieService.findAllMovies());
    }

    @PostMapping("/save")
    public ResponseEntity<Movie> saveMovie(@RequestBody Movie movie) {
        try {
            Movie movieCreated = movieService.saveMovie(movie);
            return ResponseEntity.created(new URI("/api/v1/movie/" + movieCreated.getId())).body(movie);
        } catch (URISyntaxException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> deleteMovie(@PathVariable("id") Long id_movie) {
        movieService.deleteMovie(id_movie);
        return ResponseEntity.ok(!(movieService.findMovieById(id_movie).isPresent()));
    }

}
