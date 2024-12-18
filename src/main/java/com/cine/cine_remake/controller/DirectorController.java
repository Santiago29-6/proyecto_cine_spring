package com.cine.cine_remake.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import com.cine.cine_remake.model.Director;
import com.cine.cine_remake.services.director.DirectorService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;




@RestController
@RequestMapping("api/v1/director")
public class DirectorController {

    @Autowired
    private DirectorService directorService;

    @GetMapping("/all")
    public ResponseEntity<List<Director>> getAllDirector() {
        return ResponseEntity.ok(directorService.findAll());
    }

    @PostMapping("/save")
    public ResponseEntity<Director> saveDirector(@RequestBody Director Director) {        
        try {
            Director DirectorSaved = directorService.saveDirector(Director);
            return ResponseEntity.created(new URI("/api/v1/Director/" + DirectorSaved.getId())).body(DirectorSaved);
        } catch (URISyntaxException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> deleteDirector(@PathVariable("id") Long id_film_director) {
        directorService.deleteDirector(id_film_director);
        return ResponseEntity.ok(!(directorService.findDirectorById(id_film_director).isPresent()));
    }    

}
