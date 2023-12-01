package com.DataRockect.ProjectTasks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProjectTasksRetriveService {
	
	@Autowired
	JdbcTemplate jt;
	
	public List<Map<String, Object>> retriveData(ProjectTasks projectTasks) {
		String role="",status="";
		Map<String,Object> message=new HashMap<String,Object>();
		List<Map<String,Object>> table=new ArrayList<Map<String,Object>>();
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
					String projectstatus=projectTasks.getStatus();
					String duration=projectTasks.getDuration();
					String projectid=projectTasks.getProjectId();
					String sql="select * from project_tasks where 9=9 ";
					if(projectstatus!=null) {
						sql=sql+" and status='"+projectstatus+"'";
					}if(duration!=null) {
						sql=sql+" and duration='"+duration+"'";
					}if(projectid!=null) {
						sql=sql+" and project_id='"+projectid+"'";
					}
					table=jt.queryForList(sql);
				}else {
					message.put("Error",(Object)"You Are Not Assigned To The  Project.");
					table.add(message);
				}
			}else {
				message.put("Error",(Object)"You Are Not Allowed TO View The Data");
				table.add(message);
			}
		}else {
			message.put("Error",(Object)"Invalid Credentials");
			table.add(message);
		}
		return table;
	}

}
