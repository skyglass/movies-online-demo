package skyglass.composer.movie.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import skyglass.composer.movie.model.Movie;

public interface MovieRepository extends MongoRepository<Movie, String> {
}