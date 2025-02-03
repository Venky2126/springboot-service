package com.otp.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender javaMailSender;

	public void sendOtpMessage(String to, String subject, String message) throws MessagingException {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
		helper.setFrom("tech.venkat26@gmail.com");
		helper.setTo("tech.venkat21@gmail.com");
		helper.setSubject("OTP for your account");
		helper.setText(message, true);
		javaMailSender.send(mimeMessage);

	}

}
