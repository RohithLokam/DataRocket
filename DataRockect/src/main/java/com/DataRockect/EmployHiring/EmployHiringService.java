package com.DataRockect.EmployHiring;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class EmployHiringService {
	@Autowired
	JdbcTemplate jdbctemplate;
	
	public Map<String, String> searching(EmployHiring employHiring) {
		Map<String,String> message=new HashMap<String,String>();
		String username=employHiring.getUserName();
		String password=employHiring.getPassword();
		String user_role="",roleid="";
		String sql="select * from SearchData where username=? and password=?";
		List<Map<String,Object>> table=new ArrayList<Map<String,Object>>();
		table=jdbctemplate.queryForList(sql,username,password); 
		for(Map<String,Object> info:table) {
			user_role=(String)info.get("role");	
		}
		if(table.isEmpty()){
			message.put("Error ","invalid credientials");
		}
		else {
			try {
				if(user_role.contentEquals("A") || user_role.contentEquals("H")) {
					int empid=employHiring.getEmpId();
					String firstname=employHiring.getFirstName();
					String lastname=employHiring.getLastName();
					long phonenumber=employHiring.getPhoneNumber();
					int zipcode=employHiring.getZipCode();
					String department=employHiring.getDepartment();
					String role=employHiring.getRole();
					String dob=employHiring.getDob();
					String joindate=employHiring.getJoinDate();
					String status="B";
					username=firstname.substring(0,1)+lastname+dob.substring(0,2)+dob.substring(3,5)+dob.substring(8,10);
					DateTimeFormatter dtf=DateTimeFormatter.ofPattern("hhmm");
					LocalDateTime ldt=LocalDateTime.now();
					String dat=dtf.format(ldt);
					password=Character.toUpperCase(firstname.charAt(0))+firstname.substring(1,4)
					+lastname.charAt(lastname.length()-3)+lastname.charAt(lastname.length()-2)
					+lastname.charAt(lastname.length()-1)+dat;
					String email=firstname.substring(0,1)+lastname+"@miraclesoft.com";
					String seequel="insert into SearchData values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
					String sq="select * from Ids where rolee=?";
					table=jdbctemplate.queryForList(sq,role);
					for(Map<String,Object> info:table) {
						roleid=(String)info.get("roleId");
					}
					if(roleid==null || roleid=="") {
						message.put("Warn ","role is invalid");
					}else {
						int i= jdbctemplate.update(seequel,empid,firstname,lastname,phonenumber,zipcode,department,roleid,dob,username,password,email,joindate,status);
						if(i>0) {
							message.put("Info ","employHiring inserted");
						}
					}	
				}else {
					message.put("Warn "," You Do Not have Access To Enter  Employ employHiring ");
				}
			}
			catch(Exception e) {
				message.put("Error ",e.getMessage());
			}
		}
		return  message;
	}
}
