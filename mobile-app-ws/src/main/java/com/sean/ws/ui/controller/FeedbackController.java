package com.sean.ws.ui.controller;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sean.ws.config.EmailConfig;
import com.sean.ws.ui.model.request.Feedback;

@RestController
@RequestMapping("/feedback")
public class FeedbackController {
	
	@Autowired
	private EmailConfig emailConfig;
	
	public FeedbackController(EmailConfig emailConfig) {
		super();
		this.emailConfig = emailConfig;
	}

	@PostMapping
	public String sendFeedback(@RequestBody Feedback feedback,
			BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			throw new ValidationException("Feedback is not valid");
		}
		
		// create a mail sender
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost(this.emailConfig.getHost());
		mailSender.setPort(this.emailConfig.getPort());
		mailSender.setUsername(this.emailConfig.getUsername());
		mailSender.setPassword(this.emailConfig.getPassword());
		
		// create a mail instance
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setFrom(feedback.getEmail());
		mailMessage.setTo("happylim99@gmail.com");
		mailMessage.setSubject("New feedback from " + feedback.getName());
		mailMessage.setText(feedback.getFeedback());
		
		// send mail
		mailSender.send(mailMessage);
		
		return "Email Sent";
	}

}
