package com.example.icecreamfactory;

import com.example.icecreamfactory.entity.Role;
import com.example.icecreamfactory.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class IcecreamfactoryApplication  {
	@Autowired
	private RoleRepository roleRepository;

	public static void main(String[] args) {
		SpringApplication.run(IcecreamfactoryApplication.class, args);
	}
	@Bean
	public CommandLineRunner initializeRoles() {
		return args -> {
			// Check if roles exist
			if (roleRepository.findByName("ROLE_ADMIN") == null) {
				Role adminRole = new Role();
				adminRole.setName("ROLE_ADMIN");
				roleRepository.save(adminRole);
			}

			if (roleRepository.findByName("ROLE_USER") == null) {
				Role userRole = new Role();
				userRole.setName("ROLE_USER");
				roleRepository.save(userRole);
			}
		};
	}
}
