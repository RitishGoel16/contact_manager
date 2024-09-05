package com.smart.service;

import java.io.File;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.stereotype.Service;

@Service
public class EmailService {
	
//  public void sendemail(String subject,String message,String to,String from) {
//	  System.out.println("Preparing to send message");
//
//		String  ;
//		String subject = "Codersarea: Confirmation";
//		String to = "ritishgoel2002@gmail.com";
//		String from = "goelritish19@gmail.com";
//
//       	SendEmail(message1, subject1, to1, from);
//		sendattach(message1, subject1, to1, from);
//	}

//this method send the attchment in email
//	public boolean sendemail(String message, String subject, String to) {
//		// TODO Auto-generated method stub
//		boolean f=false;
//		String host = "smtp.gmail.com";
//		String from = "goelritish19@gmail.com";
//		Properties properties = new Properties();
//		properties.put("mail.smtp.host", host);
//		properties.put("mail.smtp.port", "587");
//		properties.put("mail.smtp.auth", "true");
//		properties.put("mail.smtp.starttls.enable", "true");
//
//		// Use environment variables for sensitive data
//		String username = System.getenv("EMAIL_USERNAME");
//		String password = System.getenv("EMAIL_PASSWORD");
//
//		Session session = Session.getInstance(properties, new Authenticator() {
//			@Override
//			protected PasswordAuthentication getPasswordAuthentication() {
//				return new PasswordAuthentication("goelritish19@gmail.com", "oeog udat qybg jpmv");
//			}
//		});
//
//		session.setDebug(true);
//
//		/// home/dmdd/Pictures/spices.png
//		MimeMessage mimeMessage = new MimeMessage(session);
//		try {
//			mimeMessage.setFrom(new InternetAddress(from));
//			mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
//			mimeMessage.setSubject(subject);
//
//          //file path
//			String path = "/home/dmdd/Pictures/spices.png";
//			
//			MimeMultipart MimeMultipart=new MimeMultipart();
//			
//			MimeBodyPart textmine=new MimeBodyPart();
//			MimeBodyPart filemine=new MimeBodyPart();
//
//			try {
//				textmine.setText(message);
//				File file=new File(path);
//				filemine.attachFile(file);
//				
//				MimeMultipart.addBodyPart(textmine);
//				MimeMultipart.addBodyPart(filemine);
//			}catch(Exception e) {
//				e.printStackTrace();
//			}
//			
//			mimeMessage.setContent(MimeMultipart);
//			Transport.send(mimeMessage);
//			System.out.println("Sent successfully");
//			f=true;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return f;
//	}

	   public  boolean SendEmail(String message, String subject, String to) {
		   boolean f=false;
		String host = "smtp.gmail.com";
		String from = "goelritish19@gmail.com";
		Properties properties = new Properties();
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", "587");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");

		// Use environment variables for sensitive data
		String username = System.getenv("EMAIL_USERNAME");
		String password = System.getenv("EMAIL_PASSWORD");

		Session session = Session.getInstance(properties, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("goelritish19@gmail.com", "oeog udat qybg jpmv");
			}
		});

		session.setDebug(true);
		MimeMessage mimeMessage = new MimeMessage(session);
		try {
			mimeMessage.setFrom(new InternetAddress(from));
			mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			mimeMessage.setSubject(subject);
//	mimeMessage.setText(message);
			mimeMessage.setContent(message,"text/html");

			Transport.send(mimeMessage);
			System.out.println("Sent successfully");
			f=true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return f;
	
  }
}
