package com.cine.cine_remake.services.genre;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.cine.cine_remake.model.Genre;

@Service
public interface GenreService {

    List<Genre> findAllGenre();

    Optional<Genre> findGenreById(Long id_genre);

    Genre saveGenre(Genre genre);
    
    void deleteGenre(Long id);
}
