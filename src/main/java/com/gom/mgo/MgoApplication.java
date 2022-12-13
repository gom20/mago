package com.gom.mgo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MgoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MgoApplication.class, args);
	}

}
