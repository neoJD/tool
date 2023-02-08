package com.ndptest.tool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ToolApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(ToolApplication.class, args);
		System.out.println("=============================================================================================================================");
	}
}
