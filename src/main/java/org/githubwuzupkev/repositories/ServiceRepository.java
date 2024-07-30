package org.githubwuzupkev.repositories;

import org.githubwuzupkev.models.entities.ServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface ServiceRepository  extends JpaRepository<ServiceEntity,Integer> {
    Boolean existsServiceEntitiesBySkuCodeAndAndName(String skuCode, String name);
   Optional< ServiceEntity> findServiceEntitiesBySkuCode(String skuCode);
   Optional<ServiceEntity> deleteServiceEntitiesBySkuCode(String skuCode);

}
