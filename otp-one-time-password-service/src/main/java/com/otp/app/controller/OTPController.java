package com.otp.app.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.otp.app.service.EmailService;
import com.otp.app.service.OTPService;
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

	@GetMapping("/generateOtp")
	public String generateOtp() throws MessagingException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();

		int otp = otpService.generateOTP(username);
		log.info("OTP : " + otp);

		// Generate the Template to send OTP
		EmailTemplate template = new EmailTemplate("SendOTP.html");
		Map<String, String> replacements = new HashMap<>();
		replacements.put("user", username);
		replacements.put("otpnum", String.valueOf(otp));
		String message = template.getTemplate(replacements);
		emailService.sendOtpMessage("emailAddress of the person to whom OTP send", "OTP - Spring Boot", message);
		return "otppage";
	}

	@GetMapping("/validateOtp")
	public String validateOtp(@RequestParam int otpnum, Model model) {
		final String SUCCESS = "Entered Otp is valid";
		final String FAIL = "Entered Otp is NOT valid. Please Retry!";
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		// Validate the Otp
		if (otpnum >= 0) {
			int serverOtp = otpService.getOtp(username);
			if (serverOtp > 0) {
				if (otpnum == serverOtp) {
					otpService.clearOTP(username);
					return ("Entered Otp is valid");
				} else {
					return SUCCESS;
				}
			} else {
				return FAIL;
			}
		} else {
			return FAIL;
		}

	}

}
