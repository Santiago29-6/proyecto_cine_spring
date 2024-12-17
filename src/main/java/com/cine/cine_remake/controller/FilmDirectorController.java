package com.cine.cine_remake.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import com.cine.cine_remake.model.FilmDirector;
import com.cine.cine_remake.services.film_director.FilmDirectorService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;




@RestController
@RequestMapping("api/v1/filmDirector")
public class FilmDirectorController {

    @Autowired
    private FilmDirectorService filmDirectorService;

    @GetMapping("/all")
    public ResponseEntity<List<FilmDirector>> getAllFilmDirector() {
        return ResponseEntity.ok(filmDirectorService.findAll());
    }

    @PostMapping("/save")
    public ResponseEntity<FilmDirector> saveFilmDirector(@RequestBody FilmDirector filmDirector) {        
        try {
            FilmDirector filmDirectorSaved = filmDirectorService.saveFilmDirector(filmDirector);
            return ResponseEntity.created(new URI("/api/v1/filmDirector/" + filmDirectorSaved.getId())).body(filmDirectorSaved);
        } catch (URISyntaxException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> deleteFilmDirector(@PathVariable("id") Long id_film_director) {
        filmDirectorService.deleteFilmDirector(id_film_director);
        return ResponseEntity.ok(!(filmDirectorService.findFilmDirectorById(id_film_director).isPresent()));
    }    

}
