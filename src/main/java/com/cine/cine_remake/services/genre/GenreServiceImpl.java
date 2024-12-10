package com.cine.cine_remake.services.genre;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cine.cine_remake.model.Genre;
import com.cine.cine_remake.repository.GenreRepository;

@Service
public class GenreServiceImpl implements GenreService{

    @Autowired
    private GenreRepository genreRepository;

    @Override
    public List<Genre> findAllGenre(){
        return genreRepository.findAll();
    }

    @Override
    public Optional<Genre> findGenreById(Long id_genre){
        return genreRepository.findById(id_genre);
    }

    @Override
    public Genre saveGenre(Genre genre) {
        return genreRepository.save(genre);
    }

    @Override
    public void deleteGenre(Long id_genre){
        genreRepository.deleteById(id_genre);
    }

}
