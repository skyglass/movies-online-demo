package skyglass.composer.movie.service;

import java.util.List;

import skyglass.composer.movie.model.Movie;

public interface MovieService {

    Movie validateAndGetMovie(String imdbId);

    List<Movie> getMovies();

    Movie saveMovie(Movie movie);

    void deleteMovie(Movie movie);

}