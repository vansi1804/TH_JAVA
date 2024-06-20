package com.nvs.th_java.service.impl;

import com.nvs.th_java.enumerable.ERole;
import com.nvs.th_java.model.Role;
import com.nvs.th_java.model.User;
import com.nvs.th_java.repository.RoleRepository;
import com.nvs.th_java.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class DataInitializerService {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
        initRole();
        initAdminUser();
    }

    private void initRole() {
        Arrays.stream(ERole.values()).forEach(eRole -> {
            Role existingRole = roleRepository.findById(eRole.getValue()).orElse(null);
            if (existingRole == null) {
                existingRole = Role.builder()
                        .id(eRole.getValue())
                        .name(eRole.name())
                        .build();
                roleRepository.save(existingRole);
            } else if (!eRole.name().equals(existingRole.getName())) {
                existingRole.setName(eRole.name());
                roleRepository.save(existingRole);
            }
        });
    }

    private void initAdminUser() {
        if (!userRepository.existsByUsernameOrPhone("admin", "0589176839")) {
            Set<Role> roles = new HashSet<>();
            Role adminRole = roleRepository.findById(ERole.ADMIN.getValue()).orElseThrow(() -> new RuntimeException("Role not found"));
            roles.add(adminRole);

            User adminUser = User.builder()
                    .email("admin@gmail.com")
                    .username("admin")
                    .password(passwordEncoder.encode("admin"))
                    .roles(roles)
                    .phone("0589176839")
                    .build();

            userRepository.save(adminUser);
        }
    }
}
