package skyglass.composer.movie.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import skyglass.composer.movie.model.UserExtra;

public interface UserExtraRepository extends MongoRepository<UserExtra, String> {
}
