package com.hnam.web_order.ulti;


import com.hnam.web_order.entity.Role;
import com.hnam.web_order.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception{
        // Kiểm tra xem trong DB đã có role ADMIN chưa, nếu chưa thì tạo mới
        if(roleRepository.findByName("ADMIN").isEmpty()){
            Role adminRole = new Role();
            adminRole.setName("ADMIN");
            roleRepository.save(adminRole);
        }

        //kt Role cho user
        if(roleRepository.findByName("USER").isEmpty()){
            Role userRole = new Role();
            userRole.setName("USER");
            roleRepository.save(userRole);
        }

        System.out.println("Dữ liệu mẫu Roles tạo thành công!!!");
    }
}
