package com.es.app.config;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeyConfig {

	@Value("${config.public}")
	private String publicKey;

	@Value("${config.private}")
	private String privateKey;

	public RSAPublicKey getPublicKey() throws NoSuchAlgorithmException, InvalidKeySpecException {
		return convertToRSAPublicKey(publicKey);
	}

	public PrivateKey getPrivateKey() throws NoSuchAlgorithmException, InvalidKeySpecException {
		return decodePrivateKey(privateKey);
	}

	// Test error condition publickey
	public RSAPublicKey getErrorPublicKey() throws NoSuchAlgorithmException, InvalidKeySpecException {
		return convertToRSAPublicKey(null);
	}

	// convertToRSAPublicKey
	private RSAPublicKey convertToRSAPublicKey(String base64PublicKey)
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		byte[] publicKeyBytes = Base64.getDecoder().decode(base64PublicKey);

		X509EncodedKeySpec spec = new X509EncodedKeySpec(publicKeyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");

		PublicKey pKey = keyFactory.generatePublic(spec);

		if (pKey instanceof RSAPublicKey) {
			return (RSAPublicKey) pKey;
		} else {
			throw new IllegalArgumentException("Not an RSA public Key");
		}

	}

	// decodePrivateKey
	private PrivateKey decodePrivateKey(String base64PrivateKey)
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		byte[] privateKeyBytes = Base64.getDecoder().decode(base64PrivateKey);

		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");

		return keyFactory.generatePrivate(keySpec);

	}

}
