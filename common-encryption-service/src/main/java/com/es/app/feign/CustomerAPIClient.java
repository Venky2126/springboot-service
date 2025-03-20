package com.es.app.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.es.app.request.CustomerRequest;
import com.es.app.response.CustomerResponse;

@FeignClient(name = "customer-api-client", url = "${ces.registration}")
public interface CustomerAPIClient {
    @PostMapping(value = "/api/customer/add")
    public ResponseEntity<CustomerResponse> getCustomerRegistration(@RequestBody CustomerRequest customerRequest);
}
