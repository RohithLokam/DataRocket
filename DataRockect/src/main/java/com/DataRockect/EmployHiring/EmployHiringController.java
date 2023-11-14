package com.DataRockect.EmployHiring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("DataRocket")
public class EmployHiringController {
	Logger log=LoggerFactory.getLogger("hii");
	
	@Autowired
	EmployHiringService EmployHiringService;
	
	@PostMapping("Hiring_employees")
	public String searching(@RequestBody EmployHiring d) {
		return EmployHiringService.searching(d,log);
	}
	
	

}
