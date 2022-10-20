package com.esperanca.api.salessystem.repositories.security;

import com.esperanca.api.salessystem.entities.security.UserSecurityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserSecurityRepository extends JpaRepository<UserSecurityEntity, Integer> {
  Optional<UserSecurityEntity> findByUsername(String username);
}
