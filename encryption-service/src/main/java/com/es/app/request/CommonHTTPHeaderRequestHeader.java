package com.es.app.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommonHTTPHeaderRequestHeader {

	@JsonProperty("channel_id")
	private String channelIdentifier;

	@JsonProperty("transaction_id")
	private String transactionIdentifier;

	@JsonProperty("country_of_origin")
	private String countryOfOrigin;

	@JsonProperty("transaction_date_time")
	private String transactionDateTime;

	@JsonProperty("transaction_time_zone")
	private String transactionTimeZone;

}
