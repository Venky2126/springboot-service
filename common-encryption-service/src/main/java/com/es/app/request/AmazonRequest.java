package com.es.app.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class AmazonRequest {

	// body
	@JsonProperty("customer_id")
	private Long customerId;

	@JsonProperty("customer_name")
	private String customerName;

	@JsonProperty("customer_location")
	private String customerLocation;

	@JsonProperty("email_id")
	private String emailId;

	@JsonProperty("age")
	private Integer age;

	@JsonProperty("gender")
	private Character gender;

	@JsonProperty("product_price")
	private Double productPrice;

	@JsonProperty("payment_status")
	private boolean paymentStatus;

	@JsonProperty("total_orders")
	private Integer totalOrders;

	@JsonProperty("out_standing_amount")
	private float outStandingAmount;
}
