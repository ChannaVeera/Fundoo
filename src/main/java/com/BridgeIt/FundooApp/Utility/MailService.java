package com.BridgeIt.FundooApp.Utility;



import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import org.springframework.stereotype.Component;


@Component
public class MailService {
	
private JavaMailSender javaMailSender;

@Autowired
public MailService(JavaMailSender javaMailSender) {
	this.javaMailSender=javaMailSender;}

public void send(com.BridgeIt.FundooApp.user.Model.Email emailid) {
	System.out.println("Sending mail to receiver");
	SimpleMailMessage message=new  SimpleMailMessage();
	message.setTo(emailid.getTo());
	message.setSubject(emailid.getSubject());
	message.setText(emailid.getBody());

	javaMailSender.send(message);
	
	System.out.println("email sent successfully");
}
public String getlink(String link,String id) {
	return link+TokenUtility.generateToken(id);
}
}
