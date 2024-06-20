package com.learn.movie.service.services;

import com.learn.movie.service.exception.DataNotFoundException;
import com.learn.movie.service.exception.InvalidDataException;
import com.learn.movie.service.model.Movie;
import com.learn.movie.service.repository.MovieRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

//CRUD
    public Movie create(Movie movie){
        if(movie == null){
            throw new InvalidDataException(">> Empty Object to create it ::: ");
        }
        return movieRepository.save(movie);
    }

    public Movie read(Long id){
        return movieRepository.findById(id).orElseThrow(() -> new DataNotFoundException(">> Not present in DataBase to read it ::: "));
    }

    public Movie update(Movie movie){
        if(movie == null || movie.getId() == null){
            throw new InvalidDataException(">> Movie empty ::: ");
        }
        if(movieRepository.existsById(movie.getId())){
            Movie exmovie= movieRepository.getReferenceById(movie.getId());
            exmovie.setActors(movie.getActors());
            exmovie.setName(movie.getName());
            exmovie.setActors(movie.getActors());
            return movieRepository.save(exmovie);
        }else{
            throw new DataNotFoundException(">>> Not present in database to update it ::: ");
        }
    }

    public String delete(Long id){
        if(movieRepository.existsById(id)){
            movieRepository.deleteById(id);
            return "Movie deleted";
        }else{
            throw new DataNotFoundException(">>> Movie Not found to delete it ");
        }
    }
}
