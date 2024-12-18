package com.cine.cine_remake.services.director;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cine.cine_remake.model.Director;
import com.cine.cine_remake.repository.DirectorRepository;

@Service
public class DirectorServiceImpl implements DirectorService{

    @Autowired
    private DirectorRepository directorRepository;

    @Override
    public List<Director> findAll() {
        return directorRepository.findAll();
    }

    @Override
    public Optional<Director> findDirectorById(Long id_film_director) {
        return directorRepository.findById(id_film_director);
    }

    @Override
    public Director saveDirector(Director Director) {
        return directorRepository.save(Director);
    }

    @Override
    public void deleteDirector(Long id_film_director) {
        directorRepository.deleteById(id_film_director);
    }
    
}
