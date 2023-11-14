package com.DataRockect.ProjectTasks;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;


@Service
public class ProjectTasksFinishedTask {
	
	@Autowired
	JdbcTemplate jt;
	
	public String finishedTask(ProjectTasks projectTasks) {
		String message="",role="",status="";
		try {
		String username=projectTasks.getUserName();
		String password=projectTasks.getPassword();
		String seequel="select * from SearchData where username=? and password=?";
		List<Map<String,Object>> data=new ArrayList<Map<String,Object>>();
		data=jt.queryForList(seequel,username,password);
		if(!data.isEmpty()) {
			for(Map<String,Object> map:data) {
				role=(String)map.get("role");
				status=(String)map.get("status");
			}
			if(role.equals("P")) {
				if(status.equals("A")) {
					String taskid=projectTasks.getTaskId();
					String query1="update project_tasks set status='F' where task_id=?";
					String query2="update employ_task_list set status='F' where task_id=?";
					jt.update(query1,taskid);
					jt.update(query2,taskid);
					message="Task Finished ";
					
					}else {
					message="You Are Not Assign To Any Project To Completed A Task.";
				}
				
			}else {
				message="You Are Not Allowed To Completed Task.";
			}
			
		}else {
			message="Invalid Credentials";
		}
		}catch(Exception e) {
			message=e.getMessage();
		}
		return message;
	}

	

	
}
