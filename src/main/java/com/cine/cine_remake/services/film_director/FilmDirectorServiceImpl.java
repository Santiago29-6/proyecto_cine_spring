package com.cine.cine_remake.services.film_director;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cine.cine_remake.model.FilmDirector;
import com.cine.cine_remake.repository.FilmDirectorRepository;

@Service
public class FilmDirectorServiceImpl implements FilmDirectorService{

    @Autowired
    private FilmDirectorRepository filmDirectorRepository;

    @Override
    public List<FilmDirector> findAll() {
        return filmDirectorRepository.findAll();
    }

    @Override
    public Optional<FilmDirector> findFilmDirectorById(Long id_film_director) {
        return filmDirectorRepository.findById(id_film_director);
    }

    @Override
    public FilmDirector saveFilmDirector(FilmDirector filmDirector) {
        return filmDirectorRepository.save(filmDirector);
    }

    @Override
    public void deleteFilmDirector(Long id_film_director) {
        filmDirectorRepository.deleteById(id_film_director);
    }
    
}
