package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StaticController {

	@GetMapping("/login")
	public String getStaticPage() {
		return "pages/samples/login";
	}

	@GetMapping("/register")
	public String getRegisterPage() {
		return "pages/samples/register";
	}

	@GetMapping("/index")
	public String getIndexPage() {
		return "index";
	}

}
