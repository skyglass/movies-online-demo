package skyglass.composer.product.repository;

import skyglass.composer.product.model.UserExtra;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserExtraRepository extends MongoRepository<UserExtra, String> {
}
