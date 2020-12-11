package com.example.CRUDAPICheckpoint;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.persistence.criteria.CriteriaBuilder;


@SpringBootApplication
public class CrudApiCheckpointApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(CrudApiCheckpointApplication.class, args);
		Initialize init  = new Initialize();
		init.setupDataBase();

	}

}
