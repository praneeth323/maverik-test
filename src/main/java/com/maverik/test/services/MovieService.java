package com.maverik.test.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.maverik.test.configurations.ApiProperties;
import com.maverik.test.model.ApiResponse;
import com.maverik.test.model.Movie;
import com.maverik.test.repositories.MovieRepository;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class MovieService {

    private final RestTemplate restTemplate;
    private final ApiProperties apiProperties;

    private MovieRepository movieRepository;

    private ObjectMapper objectMapper = new ObjectMapper();

    private static final String MOVIE_NAME = "Titanic";

    @Autowired
    public MovieService(RestTemplate restTemplate, ApiProperties apiProperties) {
        this.restTemplate = restTemplate;
        this.apiProperties = apiProperties;
    }

    @Autowired
    public void setMovieRepository(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public ApiResponse<String> addMovies() {
        List<Movie> movies = new ArrayList<>();
        String apiUrl = apiProperties.getUrl1() + MOVIE_NAME + "?source=web";
        for (int i = 1; i <= 7; i++) {
            String response = restTemplate.getForObject(apiUrl, String.class);
            try {
                CollectionType listType = objectMapper.getTypeFactory().constructCollectionType(List.class, Movie.class);
                List<Movie> currentMovies = objectMapper.readValue(response, listType);
                movies.addAll(currentMovies);
            } catch (Exception ex) {
                Logger.getLogger("Movie Service: getMovieDetailsById").config("unable to convert the response to movie details error message "+ex.getMessage());
            }
        }

        if (!CollectionUtils.isEmpty(movies)) {
            for (Movie movie : movies) {
                saveMovie(movie.getImdbId());
            }
        }

        ApiResponse apiResponse = new ApiResponse(200, "movie details added successfully", movies);
        return apiResponse;

    }


    public void saveMovie(String imdbId) {
        movieRepository.save(getMovieDetailsById(imdbId));
    }

    public Movie getMovieDetailsById(String imddbId) {
        String apiUrl = apiProperties.getUrl2() + imddbId + "?source=web";
        String response = restTemplate.getForObject(apiUrl, String.class);
        Movie movie = null;
        try {
            movie = objectMapper.readValue(response, Movie.class);
        } catch (Exception ex) {
            Logger.getLogger("Movie Service: getMovieDetailsById").config("unable to convert the response to movie details error message " + ex.getMessage());
        }
        return movie;
    }

    public ApiResponse<String> listMovies() {
        List<Movie> movies = (List<Movie>) movieRepository.findAll();
        ApiResponse apiResponse = new ApiResponse(200, "movie details fetched successfully", movies);
        return apiResponse;

    }

    public ApiResponse<String> getById(String imdbId) {
        Optional<Movie> movie = movieRepository.findById(imdbId);
        if (movie.isPresent()) {
            ApiResponse apiResponse = new ApiResponse(200, "movie details fetched successfully", movie.get());
            return apiResponse;
        }
        return new ApiResponse(401, "movie details not exists. please try again with different id", null);
    }

    public ApiResponse<String> deleteById(String imdbId) {

        Optional<Movie> movie = movieRepository.findById(imdbId);
        if (movie.isPresent()) {
            movieRepository.deleteById(imdbId);
            ApiResponse apiResponse = new ApiResponse(200, "movie details deleted successfully", null);
            return apiResponse;
        }
        return new ApiResponse(401, "movie details not exists. please try again with different id", null);

    }

    public ApiResponse<String> add(Movie movie) {
        Movie newMovie = movieRepository.save(movie);
        ApiResponse apiResponse = new ApiResponse(200, "movie details added successfully", newMovie);
        return apiResponse;
    }

    public ApiResponse<String> update(Movie movie) {
        Optional<Movie> currentMovie = movieRepository.findById(movie.getImdbId());
        if (currentMovie.isPresent()) {
            Movie response = movieRepository.save(movie);
            ApiResponse apiResponse = new ApiResponse(200, "movie details updated successfully", response);
            return apiResponse;
        }
        return new ApiResponse(401, "movie details not updated. please try again", null);
    }
}
