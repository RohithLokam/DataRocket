package com.DataRockect.ProjectCloning;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("DataRocket")
public class ProjectCloningController {
	Logger log=LoggerFactory.getLogger(ProjectCloningController.class);
	
	@Autowired
	ProjectCloningService ProjectCloningService;
	
	
	@PostMapping("project_cloning")
	public String copying(@RequestBody ProjectCloning project) {
		return ProjectCloningService.projectcopy(project,log);
	}


}
