package com.es.app.controller;

import static com.es.app.constants.ApplicationConstants.HEADER_CHANNEL_ID;
import static com.es.app.constants.ApplicationConstants.HEADER_COUNTRY_OF_ORIGIN;
import static com.es.app.constants.ApplicationConstants.HEADER_TRANSACTION_DATE_TIME;
import static com.es.app.constants.ApplicationConstants.HEADER_TRANSACTION_ID;
import static com.es.app.constants.ApplicationConstants.HEADER_TRANSACTION_TIME_ZONE;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.es.app.config.UserEncryption;
import com.es.app.config.UserEncryptionChannel;
import com.es.app.request.AmazonRequest;
import com.es.app.request.CustomerRequest;
import com.es.app.request.EncryptedRequest;
import com.es.app.response.AmazonResponse;
import com.es.app.response.CustomerResponse;
import com.es.app.response.EncryptedResponse;
import com.es.app.service.AmazonClientService;
import com.es.app.service.CustomerClientService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
public class EncryptionController {

	private final UserEncryption encryption;
	private final CustomerClientService customerAPIClient;
	private final AmazonClientService amazonClientService;

	private final UserEncryptionChannel encryptionChannel;

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

//	//http://localhost:2000/amazon-service/api/v1/add
	@PostMapping(value = "/api/v1/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<EncryptedResponse> handleAmazonService(
			@RequestHeader(name = HEADER_CHANNEL_ID, required = true) String channelId,
			@RequestHeader(name = HEADER_TRANSACTION_DATE_TIME, required = true) String transactionDateTime,
			@RequestHeader(name = HEADER_TRANSACTION_TIME_ZONE, required = true) String transactionTimeZone,
			@RequestHeader(name = HEADER_COUNTRY_OF_ORIGIN, required = true) String countryOfOrigin,
			@RequestHeader(name = HEADER_TRANSACTION_ID, required = true) String transactionIdentifier,
			@RequestBody EncryptedRequest encryptedRequest) {

		log.info("Insdie EncryptionController handleaddAmazonService encryptedRequest : {}", encryptedRequest);

		AmazonRequest amazonRequest = encryption.decrypt(encryptedRequest, AmazonRequest.class);

		ResponseEntity<AmazonResponse> responseEntity = amazonClientService.callAmazonRegistration(channelId,
				transactionDateTime, transactionTimeZone, countryOfOrigin, transactionIdentifier, amazonRequest);

		AmazonResponse response = responseEntity.getBody();

		EncryptedResponse encryptedJson = encryption.encrypt(response);

		return new ResponseEntity<>(encryptedJson, HttpStatus.OK);

	}

	// http://localhost:2000/amazon-service/api/v1/add (Channel validation added)
	@PostMapping(value = "/api/v1/add-channel", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<EncryptedResponse> handleaddAmazonService(
			@RequestHeader(name = HEADER_CHANNEL_ID, required = true) String channelId,
			@RequestHeader(name = HEADER_TRANSACTION_DATE_TIME, required = true) String transactionDateTime,
			@RequestHeader(name = HEADER_TRANSACTION_TIME_ZONE, required = true) String transactionTimeZone,
			@RequestHeader(name = HEADER_COUNTRY_OF_ORIGIN, required = true) String countryOfOrigin,
			@RequestHeader(name = HEADER_TRANSACTION_ID, required = true) String transactionIdentifier,
			@RequestBody EncryptedRequest encryptedRequest) {

		log.info("Insdie EncryptionController handleaddAmazonService encryptedRequest : {}", encryptedRequest);

		AmazonRequest amazonRequest = encryptionChannel.decrypt(encryptedRequest, AmazonRequest.class);

		ResponseEntity<AmazonResponse> responseEntity = amazonClientService.callAmazonRegistration(channelId,
				transactionDateTime, transactionTimeZone, countryOfOrigin, transactionIdentifier, amazonRequest);

		AmazonResponse response = responseEntity.getBody();

		EncryptedResponse encryptedJson = encryptionChannel.encrypt(channelId, response);

		return new ResponseEntity<>(encryptedJson, HttpStatus.OK);

	}

	// Test http://localhost:8008/encryption-service/hello
	@GetMapping("/hello")
	public String sayHello() {
		return "Hello, Spring!";
	}

}
