package com.cine.cine_remake.services.director;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.cine.cine_remake.model.Director;

@Service
public interface DirectorService {

    List<Director> findAll();

    Optional<Director> findDirectorById(Long id_film_director);

    Director saveDirector(Director Director);

    void deleteDirector(Long id_film_director);
}
