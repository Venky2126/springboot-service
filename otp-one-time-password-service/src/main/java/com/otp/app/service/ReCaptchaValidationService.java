package com.otp.app.service;

import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.otp.app.model.ReCaptchResponseType;


//service to validate the captcha
@Service
public class ReCaptchaValidationService {

	public static final String SITE_VERIFY_URL = "https://www.google.com/recaptcha/api/siteverify";
	public static final String RECAPTCHA_SECRET = "6LcqmM8qAAAAAL1AEmQ-2kOLpfrr2DZ0ekawTVhK";

	public boolean validateCaptcha(String captchaResponse) {
		RestTemplate restTemplate = new RestTemplate();
		MultiValueMap<String, String> requestMap = new LinkedMultiValueMap<>();
		requestMap.add("secret", RECAPTCHA_SECRET);
		requestMap.add("response", captchaResponse);

		ReCaptchResponseType apiResponse = restTemplate.postForObject(SITE_VERIFY_URL, requestMap,
				ReCaptchResponseType.class);

		if (apiResponse == null) {
			return false;
		}

		return Boolean.TRUE.equals(apiResponse.isSuccess());
	}

}
