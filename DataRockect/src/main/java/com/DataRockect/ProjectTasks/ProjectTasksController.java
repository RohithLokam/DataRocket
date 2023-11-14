package com.DataRockect.ProjectTasks;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("DataRocket")
public class ProjectTasksController {
	
	@Autowired
	ProjectTasksCreateService ProjectTasksCreateService;
	@Autowired
	ProjectTasksUpdateService ProjectTasksUpdateService;
	@Autowired
	ProjectTasksDeleteService ProjectTasksDeleteService;
	@Autowired
	ProjectTasksFinishedTask ProjectTasksFinishedTask;
	@Autowired
	ProjectTasksRetriveService ProjectTasksRetriveService;
	
	@PostMapping("creating_tasks")
	public String creating_tasks(@RequestBody ProjectTasks pt) {
		return ProjectTasksCreateService.creatingTasks(pt);
	}
	
	@PostMapping("updating_tasks")
	public String updating_tasks(@RequestBody ProjectTasks pt) {
		return ProjectTasksUpdateService.updatingTasks(pt);
	}
	
	@DeleteMapping("deleting_tasks")
	public String deletin_tasks(@RequestBody ProjectTasks pt) {
		return ProjectTasksDeleteService.deletingTasks(pt);
	}
	
	@PostMapping("finished_task")
	public String finished_task(@RequestBody ProjectTasks pt) {
		return ProjectTasksFinishedTask.finishedTask(pt);
	}
	
	@GetMapping("retrive_data")
	public List<Map<String, Object>>  retrive_data(@RequestBody ProjectTasks pt) {
		return ProjectTasksRetriveService.retriveData(pt);
	}
}
