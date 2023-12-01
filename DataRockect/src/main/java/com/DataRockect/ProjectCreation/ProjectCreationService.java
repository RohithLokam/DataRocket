package com.DataRockect.ProjectCreation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProjectCreationService {

	@Autowired
	JdbcTemplate jdbctemplate;

	public String creating(ProjectCreation projectCreation,Logger log) {
        long startTime = System.currentTimeMillis();
		String message="",managerStatus="",hrStatus="";
		String username=projectCreation.getUserName();
		String password=projectCreation.getPassword();
		List<Map<String,Object>> employdetails=new ArrayList<Map<String,Object>>();
		String seequel="select * from SearchData where username=? and password=? and role='A' ";
		employdetails=jdbctemplate.queryForList(seequel,username,password);
		if(employdetails.isEmpty()) {
			message="invalid credentials";
            long executionTime = System.currentTimeMillis() - startTime;
			log.error(message+" Execution time: {} ms", executionTime);
		}else {
			try {
				String projectName=projectCreation.getProjectName();
				String description=projectCreation.getDescription();
				String startDate=projectCreation.getStartDate();
				String endDate=projectCreation.getEndDate();
				String requiredEmployes=projectCreation.getRequiredEmployes();
				String projectManager=projectCreation.getProjectManager();
				String hr=projectCreation.getHr();
				String projectId=projectName+startDate.substring(0,2)+startDate.substring(3,5)+startDate.substring(6,10);
				String assignedBy=username;

				String projectmanagerdetailsquery="select * from SearchData where username=? and role='P' ";
				String hrdetailsquery="select * from SearchData where username=? and role='H' ";
				List<Map<String,Object>> projectmanagerdetails=new ArrayList<Map<String,Object>>();
				List<Map<String,Object>> hrdetails=new ArrayList<Map<String,Object>>();

				projectmanagerdetails=jdbctemplate.queryForList(projectmanagerdetailsquery,projectManager);
				hrdetails=jdbctemplate.queryForList(hrdetailsquery,hr);

				if(projectmanagerdetails.isEmpty()) {
					message=" Invalid project manager details.  \n";
		            long executionTime = System.currentTimeMillis() - startTime;
					log.warn(message+"  Execution time: {} ms", executionTime);
				}if(hrdetails.isEmpty()) {
					message=message+" Invalid hr details. ";
		            long executionTime = System.currentTimeMillis() - startTime;
					log.warn(message+"  Execution time: {} ms", executionTime);
				}if(!projectmanagerdetails.isEmpty() && !hrdetails.isEmpty()) {
					for(Map<String,Object> map:projectmanagerdetails) {
						managerStatus=(String)map.get("status");
					}
					for(Map<String,Object> map:hrdetails) {
						hrStatus=(String)map.get("status");
					}
					if(managerStatus.equalsIgnoreCase("A") ) {
						message="project manager currently not available  \n";
			            long executionTime = System.currentTimeMillis() - startTime;
						log.warn(message+"  Execution time: {} ms", executionTime);
					}if(hrStatus.equalsIgnoreCase("A")) {
						message=message+"hr currently not available ";
			            long executionTime = System.currentTimeMillis() - startTime;
						log.warn(message+"  Execution time: {} ms", executionTime);
					}if(managerStatus.equalsIgnoreCase("B") && hrStatus.equalsIgnoreCase("B")) {
						String insertquery="insert into project_details values(?,?,?,?,?,?,?,?,?)";
						int i=jdbctemplate.update(insertquery,projectId,projectName,description,startDate,endDate,requiredEmployes,projectManager,hr,assignedBy);
						String updatequery="update SearchData set status='A' where username=? or username=?";
						jdbctemplate.update(updatequery,projectManager,hr);			
						if(i>0) {
							message="project created successfully";
				            long executionTime = System.currentTimeMillis() - startTime;
							log.info(message+"  Execution time: {} ms", executionTime);
						}else {
							message="project not created";
				            long executionTime = System.currentTimeMillis() - startTime;
							log.error(message+"  Execution time: {} milliseconds", executionTime);
						}	
					}else {
						message="manager/hr not available";
			            long executionTime = System.currentTimeMillis() - startTime;
						log.warn(message+"  Execution time: {} ms", executionTime);
					}
				}
			}
			catch(Exception exception) {
				message=exception.getMessage();
	            long executionTime = System.currentTimeMillis() - startTime;
				log.warn(message+"  Execution time: {} ms", executionTime);
			}
		}
        long executionTime = System.currentTimeMillis() - startTime;
		return message+"  Execution time: {} ms"+ executionTime;
	}
}



