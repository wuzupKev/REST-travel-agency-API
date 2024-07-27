package org.githubwuzupkev.repositories;

import org.githubwuzupkev.models.entities.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity ,Integer> {
    Optional<CustomerEntity> findCustomerEntitiesByIdentityCardNumber(String dni);
    Boolean existsCustomerEntitiesByIdentityCardNumber(String dni);
    Boolean existsCustomerEntitiesById(Integer id);
}
