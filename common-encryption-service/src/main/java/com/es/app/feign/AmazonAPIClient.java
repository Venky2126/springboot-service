package com.es.app.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.es.app.request.AmazonRequest;
import com.es.app.response.AmazonResponse;

@FeignClient(name = "amazon-api-client", url = "${ces.amazon}")
public interface AmazonAPIClient {

	//http://localhost:2000/amazon-service/api/v1/add
	@PostMapping(value = "/api/v1/add")
	public ResponseEntity<AmazonResponse> getAmazonRegistration(
			@RequestHeader("channel_identifer") String channelIdentifier,
			@RequestHeader("transaction_id") String transactionIdentifier,
			@RequestHeader("country_of_origin") String countryOfOrigin,
			@RequestHeader("transaction_date_time") String transactionDateTime,
			@RequestHeader("transaction_time_zone") String transactionTimeZone,
			@RequestBody AmazonRequest amazonRequest);
}
