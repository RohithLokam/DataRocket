package com.DataRockect.ProjectMeetings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("DataRocket")
public class ProjectMeetingsController {
	
	@Autowired
	ProjectMeetingsService ProjectMeetingsService;
	
	@Autowired
	SendMail sm;
	
	@PostMapping("create_meeting")
	public String create_meeting(@RequestBody ProjectMeetings pm) {
		return ProjectMeetingsService.createMeeting(pm);
	}
	
	@PostMapping("sendmail")
	public String sending_mail() {
		return sm.sendMail();
	}

}
