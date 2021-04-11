package org.practices.demo;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableAutoConfiguration
//@Configurable
public class SpringbootBestPracticesApplication {

	public static void main(String [] args) {
		SpringApplication.run(SpringbootBestPracticesApplication.class, args);
	}

}
