package com.chat.app.service;

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

	public void sendChatMessage(String toAddress, String message) throws  MessagingException {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
		helper.setFrom("tech.venkat26@gmail.com");
		if (toAddress == null) {
		    throw new IllegalArgumentException("To address must not be null");
		}
		helper.setTo(toAddress);
		helper.setSubject("We Chat!");
		helper.setText(message, true);
		javaMailSender.send(mimeMessage);

	}

}
