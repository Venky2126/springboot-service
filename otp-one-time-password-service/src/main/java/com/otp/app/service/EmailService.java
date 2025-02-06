package com.otp.app.service;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class EmailService {

	private final JavaMailSender javaMailSender;

	public void sendOtpMessage(String to, String message) throws MessagingException {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
		helper.setFrom("tech.venkat26@gmail.com");
		helper.setTo(to);
		helper.setSubject("OTP Confirmation!");
		helper.setText(message, true);
		javaMailSender.send(mimeMessage);

	}

}
