package com.es.app.config;

import java.io.InputStream;

import com.es.app.response.CommonResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomeErrorCode implements ErrorDecoder {

	private ErrorDecoder errorDecoder = new Default();

	@Override
	public Exception decode(String methodKey, Response response) {
		CommonResponse messaage = null;

		try (InputStream bodyIs = response.body().asInputStream()) {

			ObjectMapper objectMapper = new ObjectMapper();
			messaage = objectMapper.readValue(bodyIs, CommonResponse.class);

			log.info("CustomeErrorCode messaage : {}", messaage);

		} catch (Exception e) {
			return new Exception(e.getMessage());
		}
		switch (response.status()) {
		case 500:
			throw new RuntimeException();
		default:
			return errorDecoder.decode(methodKey, response);
		}
	}

}
