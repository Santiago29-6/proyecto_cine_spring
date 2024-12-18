package com.cine.cine_remake.services.actor;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.cine.cine_remake.model.Actor;

@Service
public interface ActorService {

    List<Actor> findAllActor();

    Optional<Actor> findActorById(Long id_actor);

    Actor saveActor(Actor actor);

    void deleteActor(Long id_actor);
}
