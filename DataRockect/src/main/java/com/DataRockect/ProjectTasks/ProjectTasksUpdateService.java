package com.DataRockect.ProjectTasks;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProjectTasksUpdateService {

	@Autowired
	JdbcTemplate jt;

	public String updatingTasks(ProjectTasks projectTasks) {
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
							String description=projectTasks.getDescription();
							String duration=projectTasks.getDuration();
							String sql="update project_tasks set description=?,duration=?,status=? where task_id=?";
							int i=jt.update(sql,description,duration,"C",taskid);
							if(i>0) {
								message="Task Is Updated.";
								String query="update employ_task_list set status='A' where task_id=?";
								jt.update(query,taskid);
							}else {
								message="Invaild Task Id.";
							}
						}else {
							message="Its Not Your Task To Update";
						}
					}else {
						message="You Are Not Assigned To Any Project To Update Tasks.";
					}
				}else {
					message="You Do Not Have Access TO Update Tasks.";
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