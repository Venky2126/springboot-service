package com.es.app.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



/**
 * The Class CommonResponse.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommonResponse {

	/** The error code. */
	@JsonProperty("error_code")
	private String errorCode;

	/** The error text. */
	@JsonProperty("error_text")
	private String errorText;
}
