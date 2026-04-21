package com.example.demo.authservice.repositories;

import com.example.demo.authservice.Entities.RoleEntity;
import com.example.demo.authservice.Entities.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleEntityRepository extends JpaRepository<RoleEntity, Long> {
    @Override
    Optional<RoleEntity> findById(Long aLong);

    Optional<RoleEntity> findByName(Role role);
}