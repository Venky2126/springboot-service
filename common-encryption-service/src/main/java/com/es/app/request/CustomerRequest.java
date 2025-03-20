package com.es.app.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CustomerRequest {

	@JsonProperty("id")
	private Integer id;

	@JsonProperty("name")
	private String name;

	@JsonProperty("location")
	private String location;

	@JsonProperty("dob")
	private String dob;

	@JsonProperty("contact_number")
	private String contactNumber;

	@JsonProperty("email_id")
	private String emailId;

	@JsonProperty("occupation")
	private String occupation;

	@JsonProperty("marital_status")
	private String maritalStatus;

}
