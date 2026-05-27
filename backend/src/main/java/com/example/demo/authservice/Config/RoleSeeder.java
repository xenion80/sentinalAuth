package com.example.demo.authservice.Config;
import com.example.demo.authservice.Entities.RoleEntity;
import com.example.demo.authservice.Entities.enums.Role;
import com.example.demo.authservice.repositories.RoleEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RoleSeeder implements CommandLineRunner {

    private final RoleEntityRepository roleRepository;

    @Override
    public void run(String... args) {

        if(roleRepository.count() == 0){

            for(Role role : Role.values()){

                RoleEntity roleEntity =
                        new RoleEntity();

                roleEntity.setName(role);

                roleRepository.save(roleEntity);
            }

            System.out.println("Roles seeded successfully");
        }
    }
}
