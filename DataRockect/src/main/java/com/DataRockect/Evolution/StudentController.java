package com.DataRockect.Evolution;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudentController {
	
	@Autowired
	StudentService ss;
	
	@PostMapping("student_data")
	public String dataInsertion(@RequestBody Student s) {
		return ss.insert(s);
	}
}
