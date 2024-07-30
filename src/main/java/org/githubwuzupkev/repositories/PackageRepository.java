package org.githubwuzupkev.repositories;

import org.githubwuzupkev.models.entities.PackageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PackageRepository extends JpaRepository<PackageEntity, Integer> {
    Boolean existsPackageEntitiesBySkuCode(String skuCode);
    Optional<PackageEntity> findPackageEntityBySkuCode(String skuCode);
}
