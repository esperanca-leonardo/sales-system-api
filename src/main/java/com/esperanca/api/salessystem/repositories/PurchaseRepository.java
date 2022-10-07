package com.esperanca.api.salessystem.repositories;

import com.esperanca.api.salessystem.entities.PurchaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseRepository extends JpaRepository<PurchaseEntity, Integer> {
}
