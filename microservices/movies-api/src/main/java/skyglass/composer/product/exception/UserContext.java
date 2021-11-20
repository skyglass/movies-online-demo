package skyglass.composer.product.exception;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import skyglass.composer.product.configuration.SecurityContextUtils;

@Component
public class UserContext {

	public String getUsernameFromCtx() {
		return SecurityContextUtils.getUserName();
	}

	public String getUsernameFromAuthentication(Authentication authentication) {
		return authentication.getName();
	}
}
