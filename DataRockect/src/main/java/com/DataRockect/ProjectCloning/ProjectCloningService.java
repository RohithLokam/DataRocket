package com.DataRockect.ProjectCloning;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProjectCloningService {
	@Autowired
	JdbcTemplate jdbctemplate;
	public String projectcopy(ProjectCloning projectCloning,Logger log) {
		String username=projectCloning.getUserName();
		String password=projectCloning.getPassword();
		String message="";
		String projectId="";
		String seequel="select * from SearchData where username=? and password=? and role='A' ";
		List<Map<String,Object>> table=new ArrayList<Map<String,Object>>();
		table=jdbctemplate.queryForList(seequel,username,password);
		try {
			if(!table.isEmpty()) {
				String existingProjectId=projectCloning.getExistingProjectId();
				String newProjectName=projectCloning.getNewProjectName();
				String query="select * from project_details where project_id=?";
				List<Map<String,Object>> data=new ArrayList<Map<String,Object>>();
				data=jdbctemplate.queryForList(query,existingProjectId);
				if(!data.isEmpty()) {
					for(Map<String,Object> map:data) {
						String description=(String)map.get("description");
						String startDate=(String)map.get("start_date");
						String endDate=(String)map.get("end_date");
						int requiredEmployees=(int)map.get("required_employees");
						String projectManager=(String)map.get("project_manager");
						String hr=(String)map.get("Hr_manager");
						projectId=newProjectName+startDate.substring(0,2)+startDate.substring(3,5)+startDate.substring(6,10);
						String sql="insert into project_details values(?,?,?,?,?,?,?,?,?)";
						jdbctemplate.update(sql,projectId,newProjectName,description,startDate,endDate,requiredEmployees,projectManager,hr,username);
					}
					DateTimeFormatter datetimeformatter=DateTimeFormatter.ofPattern("dd/MM/YYYY");
					LocalDateTime localdatetime=LocalDateTime.now();
					String date=datetimeformatter.format(localdatetime);
					String sq="select * from project_employ_details where project_id=?";
					List<Map<String,Object>> info=jdbctemplate.queryForList(sq,existingProjectId);
					for(Map<String,Object> map:info) {
						String insertquery="insert into project_employ_details values(?,?,?,?,?,?,?,?,?)";
						jdbctemplate.update(insertquery,projectId,newProjectName,map.get("employ_id"),map.get("employ_name"),map.get("phone_number"),map.get("email"),map.get("join_date"),date,map.get("assigned_by"));
						message="Project Cloned Successfully";
						log.info(message);
					}
				}else {
					message="Project Does Not Exist .";
					log.warn(message);
				}
			}else {
				message="Invalid credentials";
				log.error(message);
			}
		}catch(Exception exception) {
			message="Project Not Cloned \n\n\n"+"Due to : "+exception.getMessage();
			log.warn(message);
		}
		return message;
	}
	

}

