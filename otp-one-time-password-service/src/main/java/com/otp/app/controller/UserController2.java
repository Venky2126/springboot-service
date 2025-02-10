package com.otp.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.otp.app.model.UserRequest;
import com.otp.app.service.ReCaptchaValidationService;
import com.otp.app.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UserController2 {

	private final UserService userService;
	private final ReCaptchaValidationService reCaptchaValidationService;

	@PostMapping("/register")
	public String handleAddUser(@Valid UserRequest userRequest,
			@RequestParam(name = "g-recaptcha-response") String captchaResponse, Model model) {
		log.info("Handling add user request");

		if (!reCaptchaValidationService.validateCaptcha(captchaResponse)) {
			model.addAttribute("message", "Please verify captcha");
			return "custome/register"; // Return to signup page if captcha validation fails
		}
		log.info("Captcha validation successful : {}", captchaResponse);

		userService.saveUser(userRequest);
		model.addAttribute("message", "User added successfully");
		log.info("User added successfully: {}", userRequest);

		return "custome/login"; // Redirect to signin page after successful registration
	}
}