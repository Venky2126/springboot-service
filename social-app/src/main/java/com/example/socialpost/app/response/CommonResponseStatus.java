package com.example.socialpost.app.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommonResponseStatus {

	@JsonProperty("response_status")
	private CommonResponse commonResponse;
}
