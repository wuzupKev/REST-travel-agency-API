package org.githubwuzupkev.repositories;

import org.githubwuzupkev.models.entities.PaymentMethodEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface PaymentMethodRepository extends JpaRepository<PaymentMethodEntity,Integer> {
    Boolean existsPaymentMethodEntitiesByNameIgnoreCase(String name);
   Optional<PaymentMethodEntity> findPaymentMethodEntitiesByNameIgnoreCase(String name);
}
