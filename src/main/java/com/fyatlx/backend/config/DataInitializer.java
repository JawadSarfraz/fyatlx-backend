package com.fyatlx.backend.config;

import com.fyatlx.backend.entity.Role;
import com.fyatlx.backend.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) {
        createRoleIfNotExists("EPC");
        createRoleIfNotExists("Construction");
        createRoleIfNotExists("Engineering");
        createRoleIfNotExists("Developer"); // ✅ new role added
    }

    private void createRoleIfNotExists(String roleName) {
        roleRepository.findByName(roleName)
            .orElseGet(() -> roleRepository.save(new Role(null, roleName)));
    }
}
