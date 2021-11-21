package skyglass.composer.movie.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import skyglass.composer.movie.exception.UserExtraNotFoundException;
import skyglass.composer.movie.model.UserExtra;
import skyglass.composer.movie.repository.UserExtraRepository;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserExtraServiceImpl implements UserExtraService {

    private final UserExtraRepository userExtraRepository;

    @Override
    public UserExtra validateAndGetUserExtra(String username) {
        return getUserExtra(username).orElseThrow(() -> new UserExtraNotFoundException(username));
    }

    @Override
    public Optional<UserExtra> getUserExtra(String username) {
        return userExtraRepository.findById(username);
    }

    @Override
    public UserExtra saveUserExtra(UserExtra userExtra) {
        return userExtraRepository.save(userExtra);
    }

}
