package org.githubwuzupkev.repositories;

import org.githubwuzupkev.models.entities.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity,Integer> {
    Optional<EmployeeEntity>findEmployeeEntitiesByIdentityCardNumber(String identityCardNumber);
}
