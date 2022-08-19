package ch.noseryoung.repetition;

import ch.noseryoung.repetition.domain.role.Role;
import ch.noseryoung.repetition.domain.user.User;
import ch.noseryoung.repetition.domain.user.UserServiceInter;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;


@SpringBootApplication
public class RepetitionApplication {

	public static void main(String[] args) {
		SpringApplication.run(RepetitionApplication.class, args);
	}

	@Bean
	CommandLineRunner run(UserServiceInter userService){
		return args -> {
			userService.createRole(new Role(null, "ROLE_USER"));
			userService.createRole(new Role(null, "ROLE_MANAGER"));
			userService.createRole(new Role(null, "ROLE_ADMIN"));
			userService.createRole(new Role(null, "ROLE_SUPER_ADMIN"));

			userService.create(new User(null, "jan", "superPW", new ArrayList<>()));
			userService.create(new User(null, "luk", "superPW", new ArrayList<>()));
			userService.create(new User(null, "sophia", "superPW", new ArrayList<>()));
			userService.create(new User(null, "cedric", "superPW", new ArrayList<>()));

			userService.addRoleToUser("jan", "ROLE_USER");
			userService.addRoleToUser("luk", "ROLE_MANAGER");
			userService.addRoleToUser("sophia", "ROLE_ADMIN");
			userService.addRoleToUser("cedric", "ROLE_SUPER_ADMIN");

		};
	}
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
