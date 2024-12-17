package com.cine.cine_remake.services.film_director;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.cine.cine_remake.model.FilmDirector;

@Service
public interface FilmDirectorService {

    List<FilmDirector> findAll();

    Optional<FilmDirector> findFilmDirectorById(Long id_film_director);

    FilmDirector saveFilmDirector(FilmDirector filmDirector);

    void deleteFilmDirector(Long id_film_director);
}
