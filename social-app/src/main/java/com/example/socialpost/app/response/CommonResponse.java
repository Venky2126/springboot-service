package com.example.socialpost.app.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CommonResponse {

	@JsonProperty("error_code")
	public Integer errorCode;

	@JsonProperty("error_text")
	public String errorText;


}
