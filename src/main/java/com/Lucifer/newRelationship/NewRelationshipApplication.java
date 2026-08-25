package com.Lucifer.newRelationship;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAwareImpl")
public class NewRelationshipApplication {

	public static void main(String[] args) {
		SpringApplication.run(NewRelationshipApplication.class, args);
	}

}
