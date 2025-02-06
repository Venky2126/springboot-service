package com.otp.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import com.otp.app.model.UserRequest;
import com.otp.app.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UserController {

	private final UserService userService;;

	//To Access the SignUp Page
	@PostMapping("/signup")
	public String handleAddUser(@Valid UserRequest userRequest) {
		log.info("Handling add user request");
		userService.saveUser(userRequest);
		log.info("User added successfully : {}", userRequest);
		
		//it will redirect to  API signin
		return "signin";
	}

}
