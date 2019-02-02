package com.gerald.noddus.personcollector;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PersonCollectorApplication {

	public static void main(String[] args) {
		SpringApplication.run(PersonCollectorApplication.class, args);
	}

}

