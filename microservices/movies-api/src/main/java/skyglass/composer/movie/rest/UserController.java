package skyglass.composer.movie.rest;

import static skyglass.composer.movie.config.SwaggerConfig.BEARER_KEY_SECURITY_SCHEME;

import java.util.Map;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import skyglass.composer.movie.config.SecurityContextUtils;
import skyglass.composer.movie.runner.KeycloakInitializerRunner;

@RestController
@RequestMapping("/api/user")
@ApiResponse(description = "REST API for user information.")
@RequiredArgsConstructor
public class UserController {

	@Value("${keycloak.auth-server-url}")
	private String keycloakServerUrl;

	@Operation(description = "JWT Token")
	@GetMapping(value = "/jwt-token", produces = "text/html")
	public String jwtToken(@RequestParam String username, @RequestParam String password) {
		AccessTokenResponse tokenResponse = getAccessTokenResponse(KeycloakBuilder.builder() //
				.realm(KeycloakInitializerRunner.COMPANY_SERVICE_REALM_NAME) //
				.serverUrl(keycloakServerUrl)//
				.clientId(KeycloakInitializerRunner.MOVIES_APP_CLIENT_ID) //
				.username(username) //
				.password(password) //
				.grantType(OAuth2Constants.PASSWORD)
				.build());

		return tokenResponse == null ? null : tokenResponse.getToken();
	}

	@Operation(description = "Current User Info", security = { @SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME) })
	@GetMapping(value = "/current-user-info", produces = "application/json")
	public Map<String, Object> userInfo() {
		Map<String, Object> userAttributes = SecurityContextUtils.getUserAttributes();
		return userAttributes;
	}

	private AccessTokenResponse getAccessTokenResponse(Keycloak keycloak) {
		try {
			return keycloak.tokenManager().getAccessToken();
		} catch (Exception ex) {
			return null;
		}
	}

}
