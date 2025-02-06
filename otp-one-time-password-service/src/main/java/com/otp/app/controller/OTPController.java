package com.otp.app.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.otp.app.model.UserRequest;
import com.otp.app.service.EmailService;
import com.otp.app.service.OTPService;
import com.otp.app.service.UserService;
import com.otp.app.template.EmailTemplate;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class OTPController {

	private final OTPService otpService;
	private final EmailService emailService;
	private final UserService userService;

	@GetMapping("/generateOtp")
	public String generateOtp() throws MessagingException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();

		// Get the user details based on the username
		UserRequest userRequest = userService.getUser(username);

		log.info("OTPController UserRequest : " + userRequest);

		int otp = otpService.generateOTP(username);
		log.info("OTP : " + otp);

		// Generate the Template to send OTP
		EmailTemplate template = new EmailTemplate("SendOTP.html");
		Map<String, String> replacements = new HashMap<>();
		replacements.put("user", username);
		replacements.put("otpnum", String.valueOf(otp));
		String message = template.getTemplate(replacements);

		log.info("OTP Mail to : " + userRequest.getEmail());
		// Send Email
		emailService.sendOtpMessage(userRequest.getEmail(), message);
		return "otppage";
	}

	@GetMapping("/validateOtp")
	@ResponseBody
	public String validateOtp(@RequestParam int otpnum) {
		final String SUCCESS = "verified";
		final String FAIL = "Invalid Otp Try Again";
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		// Validate the Otp
		if (otpnum >= 0) {
			int serverOtp = otpService.getOtp(username);
			if (serverOtp > 0) {
				if (otpnum == serverOtp) {
					otpService.clearOTP(username);
					return SUCCESS;
				} else {
					return FAIL;
				}
			} else {
				return FAIL;
			}
		} else {
			return FAIL;
		}
	}
}