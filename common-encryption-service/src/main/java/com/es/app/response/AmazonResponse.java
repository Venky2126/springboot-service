package com.es.app.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@JsonPropertyOrder({ "response_status", "customer_id", "customer_name", "customer_location", "product_price",
		"total_orders" })
public class AmazonResponse {

	@JsonProperty("response_status")
	private CommonResponse commonResponse;

	@JsonProperty("customer_id")
	private Long customerId;

	@JsonProperty("customer_name")
	private String customerName;

	@JsonProperty("customer_location")
	private String customerLocation;

	@JsonProperty("product_price")
	private Double productPrice;

	@JsonProperty("total_orders")
	private Integer totalOrders;

}
