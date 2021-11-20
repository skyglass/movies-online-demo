package skyglass.composer.product.repository;

import skyglass.composer.product.model.Movie;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MovieRepository extends MongoRepository<Movie, String> {
}