package org.xjons.empleosdos.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.server.ResponseStatusException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class LogoutController {

	@GetMapping("/confirmLogout")
	public String confirmLogout() {
		// Just return the confirmation page, no processing needed here.
		return "confirmLogout";
	}

	@GetMapping("/postLogout")
	public String postLogout(HttpServletRequest request, HttpServletResponse response) {
		// Just return the confirmation page, no processing needed here.
		// Get the SecurityContextLogoutHandler from the Spring context (autowired or
		// instantiated)
		SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();

		// Perform the logout by calling the logout method
		logoutHandler.logout(request, response, null);
		return "postLogout";
	}

	@GetMapping("/performLogout")
	public ResponseEntity<String> performLogout(HttpServletRequest request, HttpServletResponse response) {
		try {
			// Get the SecurityContextLogoutHandler from the Spring context (autowired or
			// instantiated)
			SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();

			// Perform the logout by calling the logout method
			logoutHandler.logout(request, response, null);

			// Return a response entity indicating successful logout
			return ResponseEntity.ok("You have been logged out successfully.");

		} catch (Exception e) {
			// Throw a specific exception if something goes wrong during logout
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred during logout", e);
		}
	}
}
