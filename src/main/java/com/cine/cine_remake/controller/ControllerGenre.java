package com.cine.cine_remake.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cine.cine_remake.model.Genre;
import com.cine.cine_remake.services.genre.GenreService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("api/v1/genre")
public class ControllerGenre {

    @Autowired
    private GenreService genreService;

    @GetMapping("/all")
    public ResponseEntity<List<Genre>> getAllGenrs() {
        return ResponseEntity.ok(genreService.findAllGenre());
    }

    @PostMapping("/save")
    public ResponseEntity<Genre> saveGenre(@RequestBody Genre genre) {
        try {
            Genre genreSave = genreService.saveGenre(genre);
            return ResponseEntity.created(new URI("/api/v1/genre/" + genreSave.getId())).body(genreSave);
        } catch (URISyntaxException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> deleteGenre(@PathVariable("id") Long id_genre){
        genreService.deleteGenre(id_genre);
        return ResponseEntity.ok(!(genreService.findGenreById(id_genre).isPresent()));
    }
    
}
