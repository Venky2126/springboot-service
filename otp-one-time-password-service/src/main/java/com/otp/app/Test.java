package com.otp.app;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Test {

	public static void main(String[] args) {
		
		//encrypt validation
		String password = "venky";
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

		String hashedPassword = passwordEncoder.encode(password);

		System.out.println("encrypted : " + hashedPassword);
		
		//decrypt validation
		String decryptString = "$2a$10$2DjQ.lajtmXjkGCkI89MA.vnko1EQ3Q9QCwn7MrdvDrAQx.nAN7T.";

		String passwordToCheck = "venky";

		boolean matches = passwordEncoder.matches(passwordToCheck, decryptString);

		System.out.println("decrypt validation :" + matches);

	}

}
