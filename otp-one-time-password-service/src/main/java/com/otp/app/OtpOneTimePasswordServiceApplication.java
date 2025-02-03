package com.otp.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * This is the main class of the application.
 */

@EnableAutoConfiguration(exclude = { SecurityAutoConfiguration.class })
@EnableJpaRepositories
@SpringBootApplication
public class OtpOneTimePasswordServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OtpOneTimePasswordServiceApplication.class, args);
	}

}
