package com.learn.movie.service.controllers;

import com.learn.movie.service.exception.InvalidDataException;
import com.learn.movie.service.model.Movie;
import com.learn.movie.service.services.MovieService;
import jdk.jfr.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
@RequestMapping("/movies")
public class MoviesController {

    Logger logger=Logger.getLogger(MoviesController.class.getName());
    @Autowired
    private MovieService movieService;
    @GetMapping("/{id}")
    public ResponseEntity<?> getmovie(@PathVariable Long id){
        logger.info(">> inside Get movie :::");
        Movie movie=movieService.read(id);
        return ResponseEntity.ok(movie);
    }

    @PostMapping("/create")
    public ResponseEntity<Movie> createMovie(@RequestBody Movie movie){
        logger.info(">> inside Post create movie ::: ");
        Movie createdmovie = movieService.create(movie);
        return ResponseEntity.ok(createdmovie);
    }

    @PutMapping("/update")
    public ResponseEntity<Movie> updateMovie(@RequestBody(required = false) Movie movie){
        logger.info(">> inside put update movie ::: ");
       Movie updateMovie = movieService.update(movie);
       return ResponseEntity.ok(updateMovie);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMovie(@PathVariable Long id){
        logger.info(">> inside Delete movie ::: ");
        String response = movieService.delete(id);
        return ResponseEntity.ok(response);
    }

}
