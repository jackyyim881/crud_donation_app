package com.donation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@EntityScan("com.donation.models.data")
@EnableJpaRepositories("com.donation.repository")
public class SpringCrudDonationApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCrudDonationApplication.class, args);
	}

}