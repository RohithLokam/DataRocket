package com.DataRockect.ProjectTasks;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProjectTasksDeleteService {
	
	@Autowired
	JdbcTemplate jt;
	
	public String deletingTasks(ProjectTasks projectTasks) {
		String message="", status="", role="";
		try {
			String username=projectTasks.getUserName();
			String password=projectTasks.getPassword();
			String taskid=projectTasks.getTaskId();

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
						String sq="select * from project_tasks where task_id=? and created_by=?";
						List<Map<String,Object>> info=new ArrayList<Map<String,Object>>();
						info=jt.queryForList(sq,taskid,username);
						if(!info.isEmpty()) {
							String sql="delete from project_tasks where task_id=?";
							int i=jt.update(sql,taskid);
							if(i>0) {
								message="Task Is Deleted.";
							}else {
								message="Invaild Task Id.";
							}
						}else {
							message="Its Not Your Task To Delete";
						}
					}else {
						message="You Are Not Assigned To Any Project To Delete Tasks.";
					}
				}else {
					message="You Do Not Have Access TO Delete Tasks.";
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