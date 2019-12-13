package com.sean.ws.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.sean.ws.service.EmailService;
import com.sean.ws.shared.dto.UserDto;
import com.sean.ws.ui.model.request.EmailRequestModel;

@Service
public class EmailServiceImpl implements EmailService {
	
	@Autowired
	private JavaMailSender javaMailSender;

	@Override
	public String sendEmail(EmailRequestModel emailRequestModel){
		//UnsupportedEncodingException e
		/*
		SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(emailRequestModel.getTo_address());
        msg.setSubject(emailRequestModel.getSubject());
        msg.setText(emailRequestModel.getBody());
        */
		try {
			Session session = null;
			
			MimeMessage message = new MimeMessage(session);
			
			try {
				message.setFrom(new InternetAddress("no-reply@gmail.com", "no-reply"));
		    } catch (UnsupportedEncodingException e) {
		        return e.getMessage();
		    }
			
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(emailRequestModel.getTo_address()));
			message.setSubject(emailRequestModel.getSubject());
			
			Multipart multipart = new MimeMultipart();

	        MimeBodyPart textBodyPart = new MimeBodyPart();
	        textBodyPart.setText(emailRequestModel.getBody(), "utf-8", "html");

	        MimeBodyPart attachmentBodyPart= new MimeBodyPart();
	        DataSource source = new FileDataSource("C:\\ccc\\test.txt"); // ex : "C:\\test.pdf"
	        attachmentBodyPart.setDataHandler(new DataHandler(source));
	        attachmentBodyPart.setFileName("aaa.txt"); // ex : "test.pdf"

	        multipart.addBodyPart(textBodyPart);  // add the text part
	        multipart.addBodyPart(attachmentBodyPart); // add the attachement part

	        message.setContent(multipart);
			
	        javaMailSender.send(message);
	        
	        return "Email sent";
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		} 
		
	}
	
	public void sendEmail2(EmailRequestModel emailRequestModel)
	{
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

		String myAccountEmail = "happylim99@gmail.com";
		String password = "";

		Session session = Session.getInstance(props, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(myAccountEmail, password);
			}
		});

		try {

			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress("happylim99@gmail.com"));
			message.setRecipient(Message.RecipientType.TO, new InternetAddress("happylim99@gmail.com"));
			message.setSubject("Testing Subject");
			message.setText("<h1>aaaaaaaaaaaa</h1>", "utf-8", "html");

			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public void verifyEmail(UserDto userDto)
	{
		final String SUBJECT = "Email Verification";
		// The HTML body for the email.
		final String HTMLBODY = "<h1>Please verify your email address</h1>"
				+ "<p>Thank you for registering with our mobile app. To complete registration process and be able to log in,"
				+ " click on the following link: "
				+ "<a href='http://localhost:8080/verification-service/email-verification.html?token=$tokenValue'>"
				+ "Final step to complete your registration" + "</a><br/><br/>"
				+ "Thank you! And we are waiting for you inside!";
		final String htmlBodyWithToken = HTMLBODY.replace("$tokenValue", userDto.getEmailVerificationToken());
		try {
			Session session = null;
			MimeMessage message = new MimeMessage(session);
			try {
				message.setFrom(new InternetAddress("no-reply@gmail.com", "no-reply"));
		    } catch (UnsupportedEncodingException e) {
		        //return e.getMessage();
		    }
			
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(userDto.getEmail()));
			message.setSubject(SUBJECT);
			
			Multipart multipart = new MimeMultipart();

	        MimeBodyPart textBodyPart = new MimeBodyPart();
	        textBodyPart.setText(htmlBodyWithToken, "utf-8", "html");
	        
	        MimeBodyPart attachmentBodyPart= new MimeBodyPart();
	        DataSource source = new FileDataSource("C:\\ccc\\test.txt"); // ex : "C:\\test.pdf"
	        attachmentBodyPart.setDataHandler(new DataHandler(source));
	        attachmentBodyPart.setFileName("aaa.txt"); // ex : "test.pdf"
			
	        multipart.addBodyPart(textBodyPart);  // add the text part
	        //multipart.addBodyPart(attachmentBodyPart); // add the attachement part

	        message.setContent(multipart);
			
	        javaMailSender.send(message);
	        
	        System.out.println("Email sent");
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		} 
		
	}
}
