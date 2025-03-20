package com.es.app.controller;

import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.es.app.config.KeyConfig;
import com.es.app.config.UserEncryption;
import com.es.app.request.EncryptedRequest;
import com.es.app.response.EncryptedResponse;
import com.nimbusds.jose.Payload;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
public class EncryptionController {

	private final UserEncryption encryption;
	private final KeyConfig keyConfig;

	/** Encryption */
	@PostMapping(value = "/test-encrypt")
	public EncryptedResponse handleEncryption(@RequestBody String request)
			throws NoSuchAlgorithmException, InvalidKeySpecException {

		log.info("Insdie EncryptionController handleEncryption request : {}", request);

		EncryptedResponse encryptedResponse = null;
		RSAPublicKey publicKey = keyConfig.getPublicKey();
		encryptedResponse = encryption.encryption(publicKey, request);

		return encryptedResponse;
	}

	/** Decryption */
	@PostMapping(value = "/test-decrypt")
	public String handleDecryption(@RequestBody EncryptedRequest request)
			throws NoSuchAlgorithmException, InvalidKeySpecException {

		log.info("Insdie EncryptionController handleDecryption request : {}", request);

		try {

			PrivateKey privateKey = keyConfig.getPrivateKey();
			Payload payload = encryption.decryption(request, privateKey);
			return payload.toString();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "Decryption Error";
	}

	// Test  http://localhost:8008/encryption-service/hello
	@GetMapping("/hello")
	public String sayHello() {
		return "Hello, Spring!";
	}

}
