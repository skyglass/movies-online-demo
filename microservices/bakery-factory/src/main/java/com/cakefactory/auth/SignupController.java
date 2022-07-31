package com.cakefactory.auth;

import java.util.Collections;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/signup")
class SignupController {

	private final SignupService signupService;

	public SignupController(SignupService signupService) {
		this.signupService = signupService;
	}

	@GetMapping
	String signup() {
		return "signup";
	}

	@PostMapping
	String signup(String email, String password, String addressLine1, String addressLine2, String postcode) {
		if (this.signupService.accountExists(email)) {
			return "redirect:/login";
		}

		this.signupService.register(email, password, addressLine1, addressLine2, postcode);
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(email, "",
				Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
		SecurityContextHolder.getContext().setAuthentication(token);
		return "redirect:/";
	}
}
