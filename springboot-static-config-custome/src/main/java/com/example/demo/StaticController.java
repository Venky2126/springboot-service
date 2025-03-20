package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StaticController {

	@GetMapping("/login")
	public String getStaticPage() {
		return "samples/login";
	}

	@GetMapping("/register")
	public String getRegisterPage() {
		return "samples/register";
	}

}
