package com.DataRockect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
@ComponentScan(basePackages = "com.DataRockect")
public class DataRockectApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataRockectApplication.class, args);
	}

}
