package com.donation.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.donation.models.data.Role;
import com.donation.repository.RoleRepository;

@Component
public class DataLoader implements CommandLineRunner {

    private final RoleRepository roleRepository;

    @Autowired
    public DataLoader(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Check if ROLE_USER exists, if not, create it
        if (roleRepository.findByName("ROLE_USER") == null) {
            Role userRole = new Role("ROLE_USER");
            roleRepository.save(userRole);
        }

        // Add other default roles if needed, like ROLE_ADMIN
        if (roleRepository.findByName("ROLE_ADMIN") == null) {
            Role adminRole = new Role("ROLE_ADMIN");
            roleRepository.save(adminRole);
        }
    }
}
