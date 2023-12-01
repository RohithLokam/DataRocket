package com.DataRockect.ProjectMeetings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class SendMail {
	
	@Autowired
	JavaMailSender jms;
	
	public String sendMail() {
		
		SimpleMailMessage sm=new SimpleMailMessage();
		
//		sm.setFrom("rohithl4681@gmail.com");
		sm.setTo("rohith.lokam4681@gmail.com");
		sm.setText("you have meeting at");
		sm.setSubject("project meeting");
		jms.send(sm);
		
		
		
		return "successfully sending mail";
	}
	

}
