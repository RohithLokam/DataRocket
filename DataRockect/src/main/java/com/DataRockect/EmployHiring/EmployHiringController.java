package com.DataRockect.EmployHiring;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("DataRocket")
public class EmployHiringController {
	
	@Autowired
	EmployHiringService EmployHiringService;
	
	@PostMapping("Hiring_employees")
	public Map<String, String> searching(@RequestBody EmployHiring employHiring) {
		return EmployHiringService.searching(employHiring);
	}
	
	

}
