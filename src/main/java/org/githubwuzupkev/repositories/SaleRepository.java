package org.githubwuzupkev.repositories;

import org.githubwuzupkev.models.entities.SaleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SaleRepository  extends JpaRepository<SaleEntity,Integer> {
    Boolean existsSaleEntitiesBySaleCode(String saleCode);
    Optional<SaleEntity>findSaleEntitiesBySaleCode(String salecode);
}
