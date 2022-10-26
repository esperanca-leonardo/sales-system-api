package com.esperanca.api.salessystem.repositories;

import com.esperanca.api.salessystem.entities.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<RoleEntity, Integer> {
}
