package com.ontology;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("com.ontology.mapper")
public class OntidAuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(OntidAuthApplication.class, args);
	}
}
