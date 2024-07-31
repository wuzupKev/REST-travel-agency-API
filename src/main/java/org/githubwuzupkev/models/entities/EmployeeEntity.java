package org.githubwuzupkev.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.githubwuzupkev.models.auth.UserEntity;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="employees")
public class EmployeeEntity {
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
    @Column(length =20)
    private String professionalPosition;
    private double salary;

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "credential")
    private UserEntity credential;
}
