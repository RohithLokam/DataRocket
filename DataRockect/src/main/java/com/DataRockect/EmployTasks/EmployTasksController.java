package com.DataRockect.EmployTasks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("employ_tasks")
public class EmployTasksController {

	@Autowired
	EmployTasksInsertService EmployTasksInsertService;
	
	@PostMapping("assign_task")
	public String assignTask(@RequestBody EmployTasks et) {
		return EmployTasksInsertService.assignTasks(et);
	}
}
