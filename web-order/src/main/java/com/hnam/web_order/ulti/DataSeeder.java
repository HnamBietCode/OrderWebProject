package com.hnam.web_order.ulti;


import com.hnam.web_order.entity.Role;
import com.hnam.web_order.entity.User;
import com.hnam.web_order.repository.RoleRepository;
import com.hnam.web_order.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class DataSeeder implements CommandLineRunner {
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // 1. Tạo Role ADMIN (Viết tách dòng ra cho không bị lỗi)
        if (roleRepository.findByName("ADMIN").isEmpty()) {
            Role roleAdmin = new Role();
            roleAdmin.setName("ADMIN");
            roleRepository.save(roleAdmin);
        }

        // 2. Tạo Role USER
        if (roleRepository.findByName("USER").isEmpty()) {
            Role roleUser = new Role();
            roleUser.setName("USER");
            roleRepository.save(roleUser);
        }

        // 3. Tạo tài khoản Admin mẫu
        if (!userRepository.existsByUsername("admin")) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("123456"));
            admin.setEmail("admin@gmail.com");

            Set<Role> roles = new HashSet<>();
            Role adminRole = roleRepository.findByName("ADMIN").orElse(null);
            if (adminRole != null) {
                roles.add(adminRole);
            }
            admin.setRoles(roles);

            userRepository.save(admin);
            System.out.println("----- Đã tạo tài khoản Admin mẫu: admin / 123456 -----");
        }
    }
}
