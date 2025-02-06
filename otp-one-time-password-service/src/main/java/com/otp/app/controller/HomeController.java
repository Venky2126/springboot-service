package com.otp.app.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.otp.app.service.OTPService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class HomeController {
	@Value("${spring.application.name}")
	String appName;

	public final OTPService otpService;

	@GetMapping("/")
	public String homePage(Model model) {
		String message = " Welcome to Home Page";
		model.addAttribute("appName", appName);
		model.addAttribute("message", message);
		return "signin";
	}
	
	@GetMapping("/signup")
	public String signUpPage(Model model) {
		return "signup";
	}

	@GetMapping("/dashboard")
	public String dashboard(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		model.addAttribute("username", username);
		return "dashboard";
	}

	@GetMapping("/login")
	public String login() {
		return "signin";
	}

	@GetMapping("/admin")
	public String admin(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		model.addAttribute("username", username);
		return "admin";
	}

	@GetMapping("/user")
	public String user(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		model.addAttribute("username", username);
		return "user";
	}

	@GetMapping("/aboutus")
	public String about() {
		return "aboutus";
	}

	@GetMapping("/403")
	public String error403(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		model.addAttribute("username", username);
		return "error/403";
	}

	@GetMapping(value = "/logout")
	public @ResponseBody String logout(HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			String username = auth.getName();
			// Remove the recently used OTP from server.
			otpService.clearOTP(username);
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return "redirect:/login?logout";
	}
}