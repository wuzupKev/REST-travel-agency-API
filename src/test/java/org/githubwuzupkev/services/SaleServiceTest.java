package org.githubwuzupkev.services;

import org.githubwuzupkev.exceptions.IdNotFoundException;
import org.githubwuzupkev.models.requests.SaleRequest;
import org.githubwuzupkev.repositories.CustomerRepository;
import org.githubwuzupkev.repositories.EmployeeRepository;
import org.githubwuzupkev.repositories.PackageRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SaleServiceTest {
    @Autowired
    private SaleService service;
    @Autowired
    private EmployeeRepository repository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private PackageRepository packageRepository;
    @Test
    void createSale() throws IdNotFoundException {
        SaleRequest saleRequest = new SaleRequest("4-811-395",
                "Paypal",
                "4-811-396",
                "L43H5NRC");
        service.createASale(saleRequest);
    }
    @Test
    void EmployeeTest(){

        System.out.println(this.packageRepository.findPackageEntityBySkuCode("L43H5NRC"));    }
}