package com.es.app.controller;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.es.app.config.UserEncryption;
import com.es.app.request.CustomerRequest;
import com.es.app.request.EncryptedRequest;
import com.es.app.response.CustomerResponse;
import com.es.app.response.EncryptedResponse;
import com.es.app.service.CustomerClientService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
public class EncryptionController {

	private final UserEncryption encryption;
	private final CustomerClientService customerAPIClient;

	/** API */
	// http://localhost:8004/registration-service/api/customer/add
	@PostMapping(value = "/api/customer/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<EncryptedResponse> handleCustomerRegistration(@RequestBody EncryptedRequest encryptedRequest)
			throws NoSuchAlgorithmException, InvalidKeySpecException {

		log.info("Insdie EncryptionController handleEncryption encryptedRequest : {}", encryptedRequest);

		CustomerRequest customerRequest = encryption.decrypt(encryptedRequest, CustomerRequest.class);

		ResponseEntity<CustomerResponse> responseEntity = customerAPIClient.callCustomerRegistration(customerRequest);

		CustomerResponse response = responseEntity.getBody();

		EncryptedResponse encryptedJson = encryption.encrypt(response);

		return new ResponseEntity<>(encryptedJson, HttpStatus.OK);

	}

	// Test http://localhost:8008/encryption-service/hello
	@GetMapping("/hello")
	public String sayHello() {
		return "Hello, Spring!";
	}

}
