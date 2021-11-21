package skyglass.composer.movie.rest;

import static skyglass.composer.movie.config.SwaggerConfig.BEARER_KEY_SECURITY_SCHEME;

import java.security.Principal;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import skyglass.composer.movie.model.UserExtra;
import skyglass.composer.movie.rest.dto.UserExtraRequest;
import skyglass.composer.movie.service.UserExtraService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/userextras")
public class UserExtraController {

	private final UserExtraService userExtraService;

	@Operation(security = { @SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME) })
	@GetMapping("/me")
	public UserExtra getUserExtra(Principal principal) {
		return userExtraService.validateAndGetUserExtra(principal.getName());
	}

	@Operation(security = { @SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME) })
	@PostMapping("/me")
	public UserExtra saveUserExtra(@Valid @RequestBody UserExtraRequest updateUserExtraRequest, Principal principal) {
		Optional<UserExtra> userExtraOptional = userExtraService.getUserExtra(principal.getName());
		UserExtra userExtra = userExtraOptional.orElseGet(() -> new UserExtra(principal.getName()));
		userExtra.setAvatar(updateUserExtraRequest.getAvatar());
		return userExtraService.saveUserExtra(userExtra);
	}
}
