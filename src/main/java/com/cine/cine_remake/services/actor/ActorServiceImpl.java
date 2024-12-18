package com.cine.cine_remake.services.actor;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cine.cine_remake.model.Actor;
import com.cine.cine_remake.repository.ActorRepository;

@Service
public class ActorServiceImpl implements ActorService {

    @Autowired
    private ActorRepository actorRepository;

    @Override
    public List<Actor> findAllActor() {
        return actorRepository.findAll();
    }

    @Override
    public Optional<Actor> findActorById(Long id_actor) {
        return actorRepository.findById(id_actor);
    }

    @Override
    public Actor saveActor(Actor actor) {
        return actorRepository.save(actor);
    }

    public void deleteActor(Long id_actor) {
        actorRepository.deleteById(id_actor);
    }
}
