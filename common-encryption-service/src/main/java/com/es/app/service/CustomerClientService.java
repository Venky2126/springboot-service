package com.es.app.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.es.app.feign.CustomerAPIClient;
import com.es.app.request.CustomerRequest;
import com.es.app.response.CustomerResponse;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class CustomerClientService {

	private final CustomerAPIClient apiClient;

	public ResponseEntity<CustomerResponse> callCustomerRegistration(CustomerRequest customerRequest) {

		ResponseEntity<CustomerResponse> metaResponse = apiClient.getCustomerRegistration(customerRequest);

		CustomerResponse customerResponse = metaResponse.getBody();

		log.info("CustomerClientService callCustomerRegistration customerResponse : {}", customerResponse);

		return metaResponse;

	}

}
