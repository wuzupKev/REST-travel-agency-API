package org.githubwuzupkev;

import org.githubwuzupkev.models.auth.PermissionEntity;
import org.githubwuzupkev.models.auth.RoleEntity;
import org.githubwuzupkev.models.auth.UserEntity;
import org.githubwuzupkev.models.enums.RoleEnum;
import org.githubwuzupkev.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Set;

@SpringBootApplication
public class TravelAgencyManagerApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(TravelAgencyManagerApiApplication.class, args);
	}



}
