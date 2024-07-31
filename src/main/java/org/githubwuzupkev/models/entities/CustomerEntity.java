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
@Table(name = "customers")
public class CustomerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(length =50)
    private String firstName;
    @Column(length =50)
    private String lastName;
    @Column(length =100)
    private String homeAddress;
    @Column(unique = true,length =20)
    private String identityCardNumber;
    @Column(length =10)
    private String dateBirth;
    @Column(length =20)
    private String phoneNumber;
    @Column(length =50)
    private String email;


}
