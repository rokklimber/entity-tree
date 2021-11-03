package com.example.entityTree;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;

@Profile("!standalone")
@SpringBootApplication
public class EntityTreeApplication {

	public static void main(String[] args) {
		SpringApplication.run(EntityTreeApplication.class, args);
	}

}
