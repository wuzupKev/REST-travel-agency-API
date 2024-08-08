package org.githubwuzupkev.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.githubwuzupkev.exceptions.IdNotFoundException;
import org.githubwuzupkev.models.auth.RoleEntity;
import org.githubwuzupkev.models.auth.UserEntity;
import org.githubwuzupkev.models.entities.EmployeeEntity;
import org.githubwuzupkev.models.requests.EmployeeRequest;
import org.githubwuzupkev.models.responses.CredentialResponse;
import org.githubwuzupkev.models.responses.EmployeeResponse;
import org.githubwuzupkev.repositories.EmployeeRepository;
import org.githubwuzupkev.repositories.RoleRepository;
import org.githubwuzupkev.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class EmployeeInfoService {
    private final EmployeeRepository employeeRepository;
    public final PasswordEncoder passwordEncoder;
    public final RoleRepository roleRepository;
    public final UserRepository userRepository;
    public List<EmployeeResponse> getAllEmployeeInfo() {
        List<EmployeeEntity> list = employeeRepository.findAll();
        List<EmployeeResponse> employeeResponses =list.stream().map(employeeEntity -> {
            CredentialResponse credentialResponse =
                    new CredentialResponse(employeeEntity.getCredential().getUsername(),employeeEntity.getCredential().getRoles()
                            .stream().map(RoleEntity::getRoleEnum)
                            .collect(Collectors.toList()).toString());
            EmployeeResponse employeeResponse= new EmployeeResponse(
                employeeEntity.getId(),employeeEntity.getFirstName(),employeeEntity.getLastName(),employeeEntity.getHomeAddress(),
                    employeeEntity.getIdentityCardNumber(),employeeEntity.getDateBirth(),employeeEntity.getPhoneNumber(),employeeEntity.getProfessionalPosition(),
                    credentialResponse
            );
            return employeeResponse;
        }).collect(Collectors.toList());
        return employeeResponses;
    }

    public EmployeeResponse getEmployeeById(int id) throws IdNotFoundException {
        EmployeeEntity employeeEntity = employeeRepository.findById(id).orElse(null);
        if (employeeEntity == null) {
            throw new IdNotFoundException("the id was not found");
        }else {
            CredentialResponse credentialResponse= new CredentialResponse(employeeEntity.getCredential().getUsername(), employeeEntity.getCredential().getRoles().stream().map(RoleEntity::getRoleEnum).toString());
             return  new EmployeeResponse(employeeEntity.getId(),employeeEntity.getFirstName(),employeeEntity.getLastName(),employeeEntity.getHomeAddress(),
                    employeeEntity.getIdentityCardNumber(),employeeEntity.getDateBirth(),employeeEntity.getPhoneNumber()
                    ,employeeEntity.getProfessionalPosition(),credentialResponse
                    );
        }
    }

    public EmployeeResponse updateEmployeeById(int id, EmployeeRequest employeeRequest) throws IdNotFoundException {
        EmployeeEntity employeeEntity = employeeRepository.findById(id).orElse(null);
        if (employeeEntity == null) {
            throw new IdNotFoundException("the id was not found");
        }
        else {

            Set<RoleEntity> roleEntityList = roleRepository.findRoleEntitiesByRoleEnumIn(employeeRequest.roles())
                    .stream().collect(Collectors.toSet());
            UserEntity usr = this.userRepository.findByUsername(employeeEntity.getCredential().getUsername()).get();
            employeeEntity.setFirstName(employeeRequest.firstName());
            employeeEntity.setLastName(employeeRequest.lastName());
            employeeEntity.setHomeAddress(employeeRequest.homeAddress());
            employeeEntity.setIdentityCardNumber(employeeRequest.identityCardNumber());
            employeeEntity.setDateBirth(employeeRequest.dateBirth());
            employeeEntity.setPhoneNumber(employeeRequest.phoneNumber());
            employeeEntity.setProfessionalPosition(employeeRequest.professionalPosition());
            employeeEntity.setSalary(employeeRequest.salary());
            employeeEntity.setCredential(usr);
            employeeRepository.save(employeeEntity);
            CredentialResponse credentialResponse= new CredentialResponse(employeeEntity.getCredential().getUsername(), employeeEntity.getCredential().getRoles().stream().map(RoleEntity::getRoleEnum).collect(Collectors.toList()).toString());
            return new EmployeeResponse(employeeEntity.getId(),employeeEntity.getFirstName()
                    ,employeeEntity.getLastName(),employeeEntity.getHomeAddress()
                    ,employeeEntity.getIdentityCardNumber(),
                    employeeEntity.getDateBirth(),
                    employeeEntity.getPhoneNumber(),
                    employeeEntity.getProfessionalPosition(),
                    credentialResponse);
        }
    }


}
