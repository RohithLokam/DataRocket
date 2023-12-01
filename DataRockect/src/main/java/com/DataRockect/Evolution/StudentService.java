package com.DataRockect.Evolution;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

	@Autowired
	JdbcTemplate jt;
	
	public String insert(Student s) {
		int id=s.getId();
		String name=s.getName();
		String sql="insert into students values(?,?)";
		jt.update(sql,id,name);
		return "inserted";
	}
	
}
