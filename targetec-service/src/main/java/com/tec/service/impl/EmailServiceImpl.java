package com.tec.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.tec.model.EmailTemplate;
import com.tec.service.EmailService;

@Service("emailService")
public class EmailServiceImpl implements EmailService {

	@Autowired
	protected JavaMailSender javaMailSender;

	public void setMailSender(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	public void sendMail(EmailTemplate emailTemplate, String to,HashMap<String, String> params) {
		try {

			MimeMessage message = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setTo(to);
			helper.setSubject(emailTemplate.getSubject());
			String body = emailTemplate.getContent();
			for (Map.Entry<String, String> param : params.entrySet()) {
				String key = param.getKey();
				String value = param.getValue();
				body = body.replaceAll(":" + key, value);
			}
			helper.setText(body, true);

			javaMailSender.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
}
