package com.es.app.config;

import java.security.PrivateKey;
import java.security.interfaces.RSAPublicKey;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import com.es.app.request.EncryptedRequest;
import com.es.app.response.EncryptedResponse;
import com.nimbusds.jose.EncryptionMethod;
import com.nimbusds.jose.JWEAlgorithm;
import com.nimbusds.jose.JWEHeader;
import com.nimbusds.jose.JWEObject;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.RSADecrypter;
import com.nimbusds.jose.crypto.RSAEncrypter;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserEncryption {

	// encryption
	public EncryptedResponse encryption(RSAPublicKey publicKey, String plainRequest) {
		// JWE alg and Enc
		JWEAlgorithm algorithm = JWEAlgorithm.RSA_OAEP_256;
		EncryptionMethod encryptionMethod = EncryptionMethod.A128CBC_HS256;
		KeyGenerator keyGenerator = null;
		SecretKey secretKey = null;

		try {
			// Generate AES Keys
			keyGenerator = KeyGenerator.getInstance("AES");
			keyGenerator.init(encryptionMethod.cekBitLength());
			secretKey = keyGenerator.generateKey();

			JWEHeader header = new JWEHeader(algorithm, encryptionMethod);
			Payload payload = new Payload(plainRequest);
			JWEObject jweObject = new JWEObject(header, payload);
			jweObject.encrypt(new RSAEncrypter(publicKey, secretKey));
			String jweString = jweObject.serialize();
			return new EncryptedResponse(jweString);
		} catch (Exception e) {
			log.error("Error occured in Encryption e:{}", e.getMessage());
			throw new RuntimeException("Encryption error parsing payload");
		}
	}

	// Payload decryption
	public Payload decryption(EncryptedRequest request, PrivateKey privateKey) {
		JWEObject jweObject = null;
		try {

			jweObject = JWEObject.parse(request.getEncryptedPayload());
			jweObject.decrypt(new RSADecrypter(privateKey));

		} catch (Exception e) {
			log.error("Error occured in Decryption e:{}", e.getMessage());
			throw new RuntimeException("Decryption error parsing payload");
		}
		return jweObject.getPayload();
	}

}
