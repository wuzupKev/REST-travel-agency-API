package org.githubwuzupkev.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.githubwuzupkev.models.enums.ServiceEnum;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name="services")
public class ServiceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(length = 8)
    private String skuCode;
    @Column(length = 100)
    private String name;
    @Column(length = 200)
    private String description;
    @Column(length =50 )
    private String destination;
    @Column(length =10)
    private String productDate;
    @Column(length =10)
    private double price;


    @Column(name = "type_of_service")
    @Enumerated(EnumType.STRING)
    private ServiceEnum typeOfService;

}
