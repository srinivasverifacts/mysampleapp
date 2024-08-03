package com.dss.springboot.testing.junt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootTestingJunitApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootTestingJunitApplication.class, args);
	}

@GetMapping("/")
public String getMsg(){
return "Hello how are you...?";
}
}
