package com.DataRockect.ProjectTasks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Map;

@Service
public class ProjectTasksCreateService {

	@Autowired
	JdbcTemplate jt;

	public String creatingTasks(ProjectTasks projectTasks) {
		String message="", status="", role="";
		try {
			String username=projectTasks.getUserName();
			String password=projectTasks.getPassword();
			String projectid=projectTasks.getProjectId();

			String seequel="select * from SearchData where username=? and password=?";
			List<Map<String,Object>> data=new ArrayList<Map<String,Object>>();
			data=jt.queryForList(seequel,username,password);
			if(!data.isEmpty()) {
				for(Map<String,Object> map:data) {
					status=(String)map.get("status");
					role=(String)map.get("role");
				}
				if(role.equals("P")) {
					if(status.equals("A")) {
						String sql="select * from project_details where project_id=? and project_manager=?";
						List<Map<String,Object>> table=new ArrayList<Map<String,Object>>();
						table=jt.queryForList(sql,projectid,username);
						if(!table.isEmpty()) {
							String taskname=projectTasks.getTaskName();
							String description=projectTasks.getDescription();
							String duration=projectTasks.getDuration();
							String taskstatus="C";
							DateTimeFormatter dtf=DateTimeFormatter.ofPattern("HHmm");
							LocalDateTime ldt=LocalDateTime.now();
							String taskid=taskname+dtf.format(ldt);

							String sq="insert into project_tasks values(?,?,?,?,?,?,?)";
							int i=jt.update(sq,projectid,taskid,taskname,description,duration,taskstatus,username);
							if(i>0) {
								message="Task Created Successfully";
							}
						}else {
							message="Its Not Your Project";
						}
					}else {
						message="You Are Not Assigned To Any Project To Create Tasks.";
					}
				}else {
					message="You Do Not Have Access TO Create Tasks.";
				}
			}else {
				message="Invalid Credentials";
			}
		}catch(Exception e){
			message=e.getMessage();
		}
		return message;
	}

}
