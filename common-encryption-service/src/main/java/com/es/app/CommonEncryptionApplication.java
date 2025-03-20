package com.es.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * EncryptionApplication
 */
@SpringBootApplication
@EnableFeignClients(basePackages = "com.es.app.feign")
public class CommonEncryptionApplication {

	public static void main(String[] args) {
		SpringApplication.run(CommonEncryptionApplication.class, args);
	}

}
