package com.es.app.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.es.app.feign.AmazonAPIClient;
import com.es.app.request.AmazonRequest;
import com.es.app.response.AmazonResponse;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class AmazonClientService {

	private final AmazonAPIClient apiClient;

	public ResponseEntity<AmazonResponse> callAmazonRegistration(String channelIdentifier, String transactionIdentifier,
			String countryOfOrigin, String transactionDateTime, String transactionTimeZone,
			AmazonRequest amazonRequest) {

		ResponseEntity<AmazonResponse> metaResponse = apiClient.getAmazonRegistration(channelIdentifier,
				transactionIdentifier, countryOfOrigin, transactionDateTime, transactionTimeZone, amazonRequest);

		AmazonResponse amazonResponse = metaResponse.getBody();

		log.info("AmazonClientService callAmazonRegistration amazonResponse : {}", amazonResponse);

		return metaResponse;

	}

}
