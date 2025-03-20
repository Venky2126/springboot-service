package com.es.app.config;

import java.security.interfaces.RSAPublicKey;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import com.es.app.request.EncryptedRequest;
import com.es.app.response.EncryptedResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.nimbusds.jose.EncryptionMethod;
import com.nimbusds.jose.JWEAlgorithm;
import com.nimbusds.jose.JWEHeader;
import com.nimbusds.jose.JWEObject;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.RSADecrypter;
import com.nimbusds.jose.crypto.RSAEncrypter;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class UserEncryption {

	private final KeyConfig keyConfig;
	private final ObjectMapper objectMapper;

	public EncryptedResponse encrypt(Object customeResponse) {

		log.info("In encrypt method  customeResponse :{}", customeResponse);

		try {
			ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
			String requestPayload = objectWriter.writeValueAsString(customeResponse);
			return this.encryption(requestPayload);

		} catch (Exception e) {
			log.error("Error occured in Encryption e:{}", e.getMessage());
			throw new RuntimeException("Encryption error parsing payload");
		}
	}

	// encryption
	public EncryptedResponse encryption(String plainRequest) {
		// JWE alg and Enc
		JWEAlgorithm algorithm = JWEAlgorithm.RSA_OAEP_256;
		EncryptionMethod encryptionMethod = EncryptionMethod.A128CBC_HS256;
		KeyGenerator keyGenerator = null;
		SecretKey secretKey = null;

		try {
			// Generate AES Keys

			RSAPublicKey publicKey = keyConfig.getPublicKey();

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

	public <T> T decrypt(EncryptedRequest encryptedRequest, Class<T> customeRequest) {
		log.info("In decrypt method  customeRequest :{}", customeRequest);

		try {

			Payload payload = this.decryption(encryptedRequest);

			log.info("payload after decryption payload : {} ", payload);

			return objectMapper.readValue(payload.toString(), customeRequest);

		} catch (Exception e) {
			log.error("Error occured in Decryption e:{}", e.getMessage());
			throw new RuntimeException("Decryption error parsing payload");
		}
	}

	// Payload decryption
	public Payload decryption(EncryptedRequest encryptedRequest) {
		JWEObject jweObject = null;
		try {

			jweObject = JWEObject.parse(encryptedRequest.getEncryptedPayload());
			jweObject.decrypt(new RSADecrypter(keyConfig.getPrivateKey()));

		} catch (Exception e) {
			log.error("Error occured in Decryption e:{}", e.getMessage());
			throw new RuntimeException("Decryption error parsing payload");
		}
		return jweObject.getPayload();
	}

}
