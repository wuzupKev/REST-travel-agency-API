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
	@Bean
	CommandLineRunner init(UserRepository userRepository) {
		return args -> {
			/* Create PERMISSIONS */
			PermissionEntity createPermission = PermissionEntity.builder()
					.name("CREATE")
					.build();

			PermissionEntity readPermission = PermissionEntity.builder()
					.name("READ")
					.build();

			PermissionEntity updatePermission = PermissionEntity.builder()
					.name("UPDATE")
					.build();

			PermissionEntity deletePermission = PermissionEntity.builder()
					.name("DELETE")
					.build();

			PermissionEntity refactorPermission = PermissionEntity.builder()
					.name("REFACTOR")
					.build();

			/* Create ROLES */
			RoleEntity roleAdmin = RoleEntity.builder()
					.roleEnum(RoleEnum.ADMIN)
					.permissionList(Set.of(createPermission, readPermission, updatePermission, deletePermission))
					.build();


			RoleEntity roleDeveloper = RoleEntity.builder()
					.roleEnum(RoleEnum.DEVELOPER)
					.permissionList(Set.of(createPermission, readPermission, updatePermission, deletePermission, refactorPermission))
					.build();

			/* CREATE USERS */
			UserEntity userSantiago = UserEntity.builder()
					.username("santiago")
					.password("$2a$10$cMY29RPYoIHMJSuwRfoD3eQxU1J5Rww4VnNOUOAEPqCBshkNfrEf6")
					.isEnabled(true)
					.isAccountNonExpired(true)
					.isAccountNonLocked(true)
					.isCredentialsNonExpired(true)
					.roles(Set.of(roleAdmin))
					.build();

			UserEntity userKevin = UserEntity.builder()
					.username("Kevin")
					.password("$2a$10$cMY29RPYoIHMJSuwRfoD3eQxU1J5Rww4VnNOUOAEPqCBshkNfrEf6")
					.isEnabled(true)
					.isAccountNonExpired(true)
					.isAccountNonLocked(true)
					.isCredentialsNonExpired(true)
					.roles(Set.of(roleDeveloper))
					.build();
			userRepository.saveAll(List.of(userSantiago,userKevin));
		};
	}


}
