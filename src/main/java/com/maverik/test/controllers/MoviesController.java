package com.maverik.test.controllers;

import com.maverik.test.model.ApiResponse;
import com.maverik.test.model.Movie;
import com.maverik.test.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin
@RestController
@RequestMapping(value = "/movies")
public class MoviesController {

    @Autowired
    private MovieService movieService;

    @GetMapping("/health")
    public String health() {
        return "Success";
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<String>> addMovies() {
        ApiResponse<String> response = movieService.addMovies();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/list")
    public ResponseEntity<ApiResponse<String>> listMovies() {
        ApiResponse<String> response = movieService.listMovies();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @RequestMapping(value = "/dd/{imdbId}", method = RequestMethod.GET)
    public ResponseEntity<ApiResponse<String>> getMovieById(@PathVariable String imdbId) {
        ApiResponse<String> response = movieService.getById(imdbId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @RequestMapping(value = "/id/{imdbId}", method = RequestMethod.DELETE)
    public ResponseEntity<ApiResponse<String>> deleteById(@PathVariable String imdbId) {
        ApiResponse<String> response = movieService.deleteById(imdbId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<ApiResponse<String>> add(@RequestBody Movie movie) {
        ApiResponse<String> response = movieService.add(movie);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public ResponseEntity<ApiResponse<String>> update(@RequestBody Movie movie) {
        ApiResponse<String> response = movieService.update(movie);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
