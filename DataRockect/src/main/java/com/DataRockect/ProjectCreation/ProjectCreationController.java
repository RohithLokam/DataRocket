package com.DataRockect.ProjectCreation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("DataRocket")
public class ProjectCreationController {
	Logger log=LoggerFactory.getLogger(ProjectCreationController.class);
	
	@Autowired
	ProjectCreationService ProjectCreationService;
	
	@PostMapping("creating_project")
	public String creation(@RequestBody ProjectCreation project) {
		return ProjectCreationService.creating(project,log);
		
	}

}
