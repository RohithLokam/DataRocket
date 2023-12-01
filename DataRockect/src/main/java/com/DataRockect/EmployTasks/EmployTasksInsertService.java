package com.DataRockect.EmployTasks;


import org.apache.logging.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;

@Service
public class EmployTasksInsertService {
	
	@Autowired
	JdbcTemplate jt;
	@ExceptionHandler
	public String assignTasks(EmployTasks employTasks) {
		Logger log=(Logger) LoggerFactory.getLogger(EmployTasksInsertService.class);
		String message="",role="",status="";
		int empid=0;
		String username=employTasks.getUserName();
		String password=employTasks.getPassword();
		String seequel="select * from SearchData where username=? and password=?";
		List<Map<String,Object>> data=new ArrayList<Map<String,Object>>();
		data=jt.queryForList(seequel,username,password);
		if(!data.isEmpty()) {
			for(Map<String,Object> map:data) {
				role=(String)map.get("role");
				status=(String)map.get("status");
				 empid=(int)map.get("empid");
			}
			if(role.equals("P")) {
				if(status.equals("A")) {
					String projectid=employTasks.getProjectId();
					String sql="select * from project_details where project_id=? and project_manager=?";
					List<Map<String,Object>> details=new ArrayList<Map<String,Object>>();
					details=jt.queryForList(sql,projectid,username);
					int employid=(int)employTasks.getEmployId();
					String taskid=employTasks.getTaskId();
					String queryy="select * from employ_task_list where task_id=? and employ_id=?";
					List<Map<String,Object>> infoo=new ArrayList<Map<String,Object>>();
					infoo=jt.queryForList(queryy,taskid,employid);
					if(!infoo.isEmpty()) {
						log.warn(message);
						message="employee already working on this project";
					}
					else {
					
					if(!details.isEmpty()) {
						
						String sq="select * from project_employ_details where project_id=? and employ_id=? and assigned_by=?";
						List<Map<String,Object>> table=new ArrayList<Map<String,Object>>();
						table=jt.queryForList(sq,projectid,employid,username);
						if(!table.isEmpty() && employid!=empid) {
							String query="select * from project_tasks where task_id=? and created_by=? ";
							List<Map<String,Object>> details1=new ArrayList<Map<String,Object>>();
							details1=jt.queryForList(query,taskid,username);
							if(!details1.isEmpty()) {
								String startdate=employTasks.getStartDate();
								String enddate=employTasks.getEndDate();
								String taskstatus="A";
								String enter="insert into employ_task_list values(?,?,?,?,?,?,?)";
								int i=jt.update(enter,taskid,employid,projectid,username,startdate,enddate,taskstatus);
								if(i>0) {
									log.info(message);
									message="Task Assigned Successfully";
									String update="update project_tasks set status='A' where task_id=?";
									jt.update(update,taskid);
								}
							}else {
								log.warn(message);
								message="Its Not Your Task To Assign Employees.";
								}
						}else {
							log.warn(message);
							message="Employ Not Available To Your Project";
						}
					
				}
					else {
						log.warn(message);
						message="Its Not Your Project To Assign Tasks";
					}
					}
					}else {
						log.warn(message);
					message="You Are Not Assign To Any Project To Assign A Task To The Employees";
				}
				
			}else {
				log.warn(message);
				message="You Are Not Allowed To Assign Tasks.";
			}
			
		}else {
			log.error(message);
			message="Invalid Credentials";
		}
		return message;
	}

	
}
