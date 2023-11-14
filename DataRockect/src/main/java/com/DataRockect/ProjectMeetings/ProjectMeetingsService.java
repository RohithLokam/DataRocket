package com.DataRockect.ProjectMeetings;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.transaction.support.TransactionTemplate;

@Service
public class ProjectMeetingsService {

	@Autowired
	JdbcTemplate jt;
	@Autowired
	TransactionTemplate transactionTemplate;
	@Autowired
	JavaMailSender javaMailSender;

	@Transactional
	public String createMeeting(ProjectMeetings pm) {
		String message="",userRole="",employRole="",name="",start_time="00:00",end_time="00:00";
		int userId=0,count=0;

		try {
			String username=pm.getUserName();
			String password=pm.getPassword();
			String starting_time=pm.getStartTime();
			DateTimeFormatter dfm=DateTimeFormatter.ofPattern("HH:mm");
			LocalDateTime ldt=LocalDateTime.now();
			if(starting_time==null)
				starting_time=dfm.format(ldt);
			LocalTime tim=LocalTime.parse(starting_time,dfm);
			LocalDate dat = LocalDate.now();  

			String date=""+dat;
			String query="select * from SearchData where username=? and password=?";
			List<Map<String,Object>> data=new ArrayList<Map<String,Object>>();
			data=jt.queryForList(query,username,password);
			if(!data.isEmpty()) {
				for(Map<String,Object> map:data) {
					userRole=(String)map.get("role");
					userId=(int)map.get("empid");
				}
				String meetingname=pm.getMeetingName();
				String meetingcontacts=pm.getMeetingContacts();
				String projectid=pm.getProjectId();
				String description=pm.getDescription();
				int duration=pm.getDuration();
				LocalTime timee=tim.plusMinutes(duration);
				String ending_time=timee.format(dfm);
				String sql="select * from project_employ_details where project_id=? and employ_id=?";
				List<Map<String,Object>> info=new ArrayList<Map<String,Object>>();
				info=jt.queryForList(sql,projectid,userId);
				if(!info.isEmpty()) {
					String details[]=meetingcontacts.split(",");
					for(String empid:details) {
						count++;
						String seequel="select * from project_employ_details where employ_id=? and project_id=?";
						String seequl="select role from SearchData where empid=?";
						List<Map<String,Object>> table=new ArrayList<Map<String,Object>>();
						List<Map<String,Object>> table1=new ArrayList<Map<String,Object>>();
						table=jt.queryForList(seequel,empid,projectid);
						table1=jt.queryForList(seequl,empid);
						for(Map<String,Object> map:table1) {
							employRole=(String)map.get("role");
							name=(String)map.get("firstname");
						}
						if(!table.isEmpty()) {
							if(!(userRole.equals("E") && (employRole.equals("P")|| employRole.equals("H")) )) {
								String replica="select * from project_meetings where meeting_contacts=? and date=?";
								List<Map<String,Object>> detail=new ArrayList<Map<String,Object>>();
								detail=jt.queryForList(replica,empid,date);
								for(Map<String,Object> map:detail) {
									start_time=(String)map.get("start_time");
									end_time=(String)map.get("end_time");
								}
								LocalTime startTime=LocalTime.parse(start_time,dfm);
								LocalTime endTime=LocalTime.parse(end_time,dfm);
								if((tim.isAfter(endTime) || timee.isBefore(startTime))|| detail.isEmpty()) {
									String meetingid=meetingname+starting_time.substring(0,2)+starting_time.substring(3,5);
									String queryy="insert into project_meetings values(?,?,?,?,?,?,?,?,?,?,?)";
									int i=jt.update(queryy,meetingid,meetingname,empid,projectid,starting_time,ending_time,duration,date,username,"S",description);
									if(i==count) {
										message="Meeting Sheduled Successfully";
										sendingmail(details,username,date,starting_time);
									}
								}else {
									message=message+name+" Has Already heduled  Meeting In That Time.\n";
								}
							}else {
								message="You Are Not Allowed To Conduct Meeting To The Project Manager/Hr";
							}
						}else {
							message=empid+" Employ Id Is  Not belong To Your Project";
							TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
						}
					}
				}else {
					message="Project Id Not Belongs To You";
				}
			}else {
				message="Invalid Credentials";
			}
		}catch(Exception e) {
			message=e.getMessage();
		}
		return message;
	}
	private void sendingmail(String [] details,String username,String date,String starting_time) {
		SimpleMailMessage sm=new SimpleMailMessage();
		for(String empid1:details) {
			String mail="select * from SearchData where empid=?";
			List<Map<String,Object>> mails=new ArrayList<Map<String,Object>>();
			mails=jt.queryForList(mail,empid1);
			if(!mails.isEmpty()) {
				for(Map<String,Object> map:mails) {
					String information="To: "+(String)map.get("firstname")+" "+(String)map.get("lastname") +"\n"
							+"\n"
							+ "From: "+username+"\n"
							+ "\n"
							+ "Date: "+date+"\n"
							+ "\n"
							+ "I am writing to invite you to a project meeting on "+date+" at "+starting_time+".\n"
							+ "\n"
							+ "This meeting is mandatory for all members of the project team.\n"
							+ "\n"
							+ "During the meeting, we will discuss the following:\n"
							+ "\n"
							+ "    Project status update\n"
							+ "    Upcoming deadlines and milestones\n"
							+ "    Team roles and responsibilities\n"
							+ "    Any challenges or obstacles we are facing\n"
							+ "    Strategies for success\n"
							+ "\n"
							+ "Please come prepared to share your thoughts and ideas, and to answer any questions that I may have.\n"
							+ "\n"
							+ "If you have any questions or concerns, please do not hesitate to contact me.\n"
							+ "\n"
							+ "I look forward to seeing you at the meeting.\n"
							+ "\n"
							+ "Sincerely,\n"
							+ ""+username+"";
					sm.setFrom("rohithl4681@gmail.com");
					sm.setTo((String)map.get("email"));
					sm.setText(information);
					sm.setSubject("Project meeting invitation");
					javaMailSender.send(sm);
				}
			}

		}		
	}
}
