package com.donation;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("com.donation.models.data")
@EnableJpaRepositories("com.donation.repository")
public class SpringCrudDonationApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCrudDonationApplication.class, args);
	}

	// @Bean
	// CommandLineRunner init(DonationRepository donationRepository, UserRepository
	// userRepository) {
	// return args -> {
	// // Initialization code if needed
	// };
	// }
}