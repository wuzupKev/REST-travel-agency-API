package org.githubwuzupkev.controllers;

import org.githubwuzupkev.exceptions.DuplicatedUserFoundException;
import org.githubwuzupkev.exceptions.IdNotFoundException;
import org.githubwuzupkev.models.requests.CustomerRequest;
import org.githubwuzupkev.models.responses.CustomerResponse;
import org.githubwuzupkev.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CustomerResponse>createCustomer(@RequestBody CustomerRequest customerRequest) throws DuplicatedUserFoundException {
        return ResponseEntity.ok(customerService.createCustomer(customerRequest));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<CustomerResponse>> getAllCustomers() {
        return ResponseEntity.ok(customerService.getAllCustomers());
    }

    @GetMapping("/id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CustomerResponse> getCustomerById(@PathVariable int id) throws IdNotFoundException {
        return ResponseEntity.ok(this.customerService.findById(id));
    }

    @GetMapping("/customer-identity-card-number/{icn}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CustomerResponse> getCustomerByIdentityCardNumber(@PathVariable String icn) throws IdNotFoundException {
        return ResponseEntity.ok(this.customerService.getCustomerByDni(icn));
    }

    @DeleteMapping("/delete-customer/{id}")
    public ResponseEntity<CustomerResponse> deleteCustomer(@PathVariable int id) throws IdNotFoundException {
        return ResponseEntity.ok(this.customerService.deleteById(id));
    }

    @PutMapping("/update-customer/{id}")
    public ResponseEntity<CustomerResponse> updateCustomer(@PathVariable int id,@RequestBody CustomerRequest customerRequest) throws IdNotFoundException {
        return ResponseEntity.ok(this.customerService.updateCustomer(customerRequest,id));
    }


}
