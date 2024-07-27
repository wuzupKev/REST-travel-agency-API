package org.githubwuzupkev.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.githubwuzupkev.exceptions.IdNotFoundException;
import org.githubwuzupkev.exceptions.DuplicatedUserFoundException;
import org.githubwuzupkev.models.entities.CustomerEntity;
import org.githubwuzupkev.models.requests.CustomerRequest;
import org.githubwuzupkev.models.responses.CustomerResponse;
import org.githubwuzupkev.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerResponse createCustomer(CustomerRequest customerRequest) throws DuplicatedUserFoundException {
        Boolean isCustomerExist= this.customerRepository.existsCustomerEntitiesByIdentityCardNumber(customerRequest.identityCardNumber());
        if (isCustomerExist){
            throw new DuplicatedUserFoundException("the user was already created");
        }else {
            CustomerEntity customer= CustomerEntity.builder()
                    .firstName(customerRequest.firstName())
                    .lastName(customerRequest.lastName())
                    .phoneNumber(customerRequest.phoneNumber())
                    .email(customerRequest.email())
                    .identityCardNumber(customerRequest.identityCardNumber())
                    .dateBirth(customerRequest.dateBirth())
                    .homeAddress(customerRequest.homeAddress())
                    .build();
            this.customerRepository.save(customer);
            log.info("Created new customer: "+customer);
            return new  CustomerResponse(customer.getId(),customer.getFirstName(),customer.getLastName(),customer.getHomeAddress(),
                    customer.getIdentityCardNumber(),customer.getDateBirth(),customer.getPhoneNumber(),customer.getEmail());
        }


    }

    @Transactional
    public CustomerResponse updateCustomer(CustomerRequest customerRequest,int id) throws IdNotFoundException {
        CustomerEntity customerExist =
                this.customerRepository.findById(id).orElseThrow(()-> new IdNotFoundException("the id was not found"));
        if (customerExist==null){
            throw new NullPointerException("the object is null");
        }else {
            customerExist.setFirstName(customerRequest.firstName());
            customerExist.setLastName(customerRequest.lastName());
            customerExist.setHomeAddress(customerRequest.homeAddress());
            customerExist.setIdentityCardNumber(customerRequest.identityCardNumber());
            customerExist.setDateBirth(customerRequest.dateBirth());
            customerExist.setPhoneNumber(customerRequest.phoneNumber());
            customerExist.setEmail(customerRequest.email());
            this.customerRepository.save(customerExist);
            log.info("the customer with id: "+customerExist.getId()
                    +" has been updated "+customerExist);
            return new CustomerResponse(customerExist.getId(),
                    customerExist.getFirstName(),
                    customerExist.getLastName(),
                    customerExist.getHomeAddress()
                    ,customerExist.getIdentityCardNumber(),
                    customerExist.getDateBirth(),customerExist.getPhoneNumber(),customerExist.getEmail());
        }


    }

    public CustomerResponse findById(int id) throws IdNotFoundException {
        Optional<CustomerEntity> customerExist = this.customerRepository.findById(id);
        if (!customerExist.isPresent()) {
          throw new IdNotFoundException("the id: "+id+" was not found");
        }
        CustomerEntity customer = customerExist.get();
        return new CustomerResponse(customer.getId(),customer.getFirstName(),
                customer.getLastName(), customer.getHomeAddress(),
                customer.getIdentityCardNumber(), customer.getDateBirth(),
                customer.getPhoneNumber(), customer.getEmail());
    }

    public List<CustomerResponse> getAllCustomers(){
        List<CustomerEntity> customerList= this.customerRepository.findAll();
        List<CustomerResponse> responseList=
        customerList.stream().map(customerEntity->{
            CustomerResponse customerResponse=
                    new CustomerResponse(customerEntity.getId(),customerEntity.getFirstName(),
                            customerEntity.getLastName(),
                            customerEntity.getHomeAddress(),
                            customerEntity.getIdentityCardNumber(),
                            customerEntity.getDateBirth(),
                            customerEntity.getPhoneNumber(),
                            customerEntity.getEmail());
            return customerResponse;
        }).collect(Collectors.toList());
        return responseList;
    }

    public CustomerResponse removeCustomer(int id) throws IdNotFoundException {
        Optional<CustomerEntity> customerExist = this.customerRepository.findById(id);

        if (!customerExist.isPresent()) {
           throw new IdNotFoundException("The id: "+id+" doesnt exist");
        }
        CustomerEntity customerEntity= customerExist.get();
        CustomerResponse customerResponse= new CustomerResponse(customerEntity.getId()
                ,customerEntity.getFirstName(),customerEntity.getLastName(),
                customerEntity.getHomeAddress(),customerEntity.getIdentityCardNumber(),
                customerEntity.getDateBirth(),customerEntity.getPhoneNumber(),
                customerEntity.getEmail());
        this.customerRepository.delete(customerEntity);
        return customerResponse;
    }
}
