package org.githubwuzupkev.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "sales")
public class SaleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String salesDate;
    private String saleCode;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "customer",referencedColumnName = "id")
    private CustomerEntity customer;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "payment",referencedColumnName = "id")
    private PaymentMethodEntity paymentMethod;

    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "employee" ,referencedColumnName = "id")
    private  EmployeeEntity employee;

    private Double price;

    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "packages_on_sale" ,referencedColumnName = "id")
    private PackageEntity packageOnSale;
}
