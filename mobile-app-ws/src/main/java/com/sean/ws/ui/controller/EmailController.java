package com.sean.ws.ui.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sean.ws.service.EmailService;
import com.sean.ws.ui.model.request.EmailRequestModel;

@RestController
@RequestMapping("/email-service")
public class EmailController {
	
	@Autowired
	private EmailService emailService;
	
	@PostMapping
	public String sendMail(@RequestBody EmailRequestModel emailRequestModel)
	{
		//System.out.println(Utils.fetchProperties("C:/ccc/test.txt").get("test"));
		return emailService.sendEmail(emailRequestModel);
		//return "Aa";
	}
	
	@PostMapping("/2")
	public void sendMail2(@RequestBody EmailRequestModel emailRequestModel)
	{
		emailService.sendEmail2(emailRequestModel);
	}

}
