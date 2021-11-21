package skyglass.composer.movie.service;

import java.util.Optional;

import skyglass.composer.movie.model.UserExtra;

public interface UserExtraService {

    UserExtra validateAndGetUserExtra(String username);

    Optional<UserExtra> getUserExtra(String username);

    UserExtra saveUserExtra(UserExtra userExtra);

}
