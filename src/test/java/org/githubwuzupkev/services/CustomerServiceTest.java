package org.githubwuzupkev.services;

import org.githubwuzupkev.exceptions.IdNotFoundException;
import org.githubwuzupkev.exceptions.DuplicatedUserFoundException;
import org.githubwuzupkev.models.requests.CustomerRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CustomerServiceTest {
    @BeforeEach
    void setUp() {
        System.out.println("first execute");
    }
    @AfterEach
    void tearDown() {
        System.out.println("second execute");
    }

    @Autowired
    private CustomerService customerService;

    @Test
    public void getAllCustomers() {
        System.out.println(this.customerService.getAllCustomers());;
    }

    @Test
    public void getCustomerById() throws IdNotFoundException {
        System.out.println(this.customerService.findById(2));
    }

    @Test
    public void createCustomer() throws IdNotFoundException, DuplicatedUserFoundException {
        CustomerRequest customer=
                new CustomerRequest
                            ("Josue",
                                "Vega",
                                "Panama city,Panama",
                                "5-811-395",
                                "2001-03-10",
                                "+507 65213346","josue@ds.ce");
        this.customerService.createCustomer(customer);
    }

    @Test
    public void updateCustomer() throws IdNotFoundException, DuplicatedUserFoundException {
        CustomerRequest customer=
                new CustomerRequest
                        ("Pepe",
                                "Reina",
                                "Panama city,Panama",
                                "8-411-695",
                                "2005-03-10",
                                "+507 65215346","pepeReina@ds.ce");
        this.customerService.updateCustomer(customer,1);
    }

    @Test
    public void deleteCustomer() throws IdNotFoundException{
        System.out.println(this.customerService.removeCustomer(2));
    }
}