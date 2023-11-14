package com.DataRockect.ProjectAssign;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("DataRocket")
public class ProjectAssignController {
	Logger log=LoggerFactory.getLogger(ProjectAssignController.class);

	
	@Autowired
	ProjectAssignService ProjectAssignService;
	
	
	@PostMapping("employees_assigned_project")
	public String assigning(@RequestBody ProjectAssign project) {
		return ProjectAssignService.assignemployees(project,log);
	}

}
