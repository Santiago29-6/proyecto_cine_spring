package com.cine.cine_remake.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cine.cine_remake.model.Actor;
import com.cine.cine_remake.services.actor.ActorService;

@RestController
@RequestMapping("api/v1/actor")
public class ActorController {

    @Autowired
    private ActorService actorService;

    @GetMapping("/all")
    public ResponseEntity<List<Actor>> getAllActor() {
        return ResponseEntity.ok(actorService.findAllActor());
    }

    @PostMapping("/save")
    public ResponseEntity<Actor> saveActor(@RequestBody Actor actor) {
        Actor actorSaved = actorService.saveActor(actor);
        try {
            return ResponseEntity.created(new URI("/api/v1/actor/" + actorSaved.getId())).body(actorSaved);
        } catch (URISyntaxException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    } 

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> deleteActor(@PathVariable("id") Long id_actor) {
        actorService.deleteActor(id_actor);
        return ResponseEntity.ok(!(actorService.findActorById(id_actor).isPresent()));
    }

}
