package com.tec.service;

import java.util.HashMap;

import com.tec.model.EmailTemplate;

public interface EmailService {

	public void sendMail(EmailTemplate emailTemplate,String to,HashMap<String, String> params);

}
