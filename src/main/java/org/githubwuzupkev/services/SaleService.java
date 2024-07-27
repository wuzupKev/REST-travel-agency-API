package org.githubwuzupkev.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.githubwuzupkev.exceptions.IdNotFoundException;
import org.githubwuzupkev.models.entities.*;
import org.githubwuzupkev.models.requests.SaleRequest;
import org.githubwuzupkev.models.responses.SaleResponse;
import org.githubwuzupkev.repositories.*;
import org.githubwuzupkev.tools.UsefulFunction;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class SaleService {
    private final SaleRepository saleRepository;
    private final CustomerRepository customerRepository;
    private final EmployeeRepository emloyeeRepository;
    private final PackageRepository packageRepository;
    private final PaymentMethodRepository paymentMethodRepository;

    public SaleResponse createASale(SaleRequest saleRequest) throws IdNotFoundException {
        if (saleRequest == null) {
            throw new NullPointerException("cant be null");
        }else {
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            EmployeeEntity employee= this.emloyeeRepository
                    .findEmployeeEntitiesByIdentityCardNumber(saleRequest.dniEmployee())
                    .orElseThrow(()-> new IdNotFoundException("Employee not found"));
            CustomerEntity customer= this.customerRepository.
                    findCustomerEntitiesByIdentityCardNumber(saleRequest.dniCustomer())
                    .orElseThrow(()-> new IdNotFoundException("Customer not found"));
            PackageEntity packAge = this.packageRepository
                    .findPackageEntityBySkuCode(saleRequest.skucodePackage())
                    .orElseThrow(()-> new IdNotFoundException("Package not found"));
            PaymentMethodEntity paymentMethod = this.paymentMethodRepository
                    .findPaymentMethodEntitiesByNameIgnoreCase(saleRequest.paymentMethod())
                    .orElseThrow(()-> new IdNotFoundException("Payment method not found"));
            Date date = new Date();
            SaleEntity saleEntity = SaleEntity.builder()
                    .saleCode(UsefulFunction.generateRandomChars())
                    .salesDate(dateFormat.format(date))
                    .employee(employee)
                    .customer(customer)
                    .packageOnSale(packAge)
                    .paymentMethod(paymentMethod)
                    .price(packAge.getPrice())
                    .build();
            this.saleRepository.save(saleEntity);
            return new SaleResponse(saleEntity.getId()
                    ,saleEntity.getSalesDate(),
                    saleEntity.getSaleCode(),saleEntity.getCustomer(),saleEntity.getPaymentMethod(),saleEntity.getEmployee(),
                    saleEntity.getPackageOnSale(),saleEntity.getPrice());
        }
    }

    public SaleResponse UpdateASale(SaleRequest saleRequest,int id) throws IdNotFoundException {
        PackageEntity packageEntity= this.packageRepository
                .findPackageEntityBySkuCode(saleRequest.skucodePackage()).orElseThrow(()-> new IdNotFoundException("Package not found"));
        if (saleRequest == null) {
            throw new NullPointerException("A sale cant be null");
        }
        else {
            Optional<SaleEntity> saleEntity = this.saleRepository.findById(id);
            if (!saleEntity.isPresent()) {
                throw new IdNotFoundException("Sale not found");
            }else {
                saleEntity.get().setSaleCode(saleEntity.get().getSaleCode());
                saleEntity.get().setSalesDate(saleEntity.get().getSalesDate());
                saleEntity.get().setCustomer(this.customerRepository
                        .findCustomerEntitiesByIdentityCardNumber(saleRequest.dniCustomer())
                        .orElseThrow(()-> new IdNotFoundException("Customer not found")));
                saleEntity.get().setEmployee(this.emloyeeRepository
                        .findEmployeeEntitiesByIdentityCardNumber(saleRequest.dniEmployee())
                        .orElseThrow(()-> new IdNotFoundException("Employee not found")));
                saleEntity.get().setPaymentMethod(this.paymentMethodRepository
                        .findPaymentMethodEntitiesByNameIgnoreCase(saleRequest.paymentMethod())
                        .orElseThrow(()-> new IdNotFoundException("Payment method not found")));
                saleEntity.get().setPackageOnSale(this.packageRepository
                        .findPackageEntityBySkuCode(saleRequest.skucodePackage())
                        .orElseThrow(()-> new IdNotFoundException("Package not found")));
                saleEntity.get().setPrice(packageEntity.getPrice());
                this.saleRepository.save(saleEntity.get());
                return new SaleResponse(saleEntity.get().getId()
                        ,saleEntity.get().getSalesDate(),
                        saleEntity.get().getSaleCode(),saleEntity.get().getCustomer(),
                        saleEntity.get().getPaymentMethod(),saleEntity.get().getEmployee(),
                        saleEntity.get().getPackageOnSale(),saleEntity.get().getPrice());
            }
        }

    }

    public SaleResponse findByCode(String code){
        Optional<SaleEntity> saleEntity= this.saleRepository.findSaleEntitiesBySaleCode(code);
        if (!saleEntity.isPresent()){
            throw new NullPointerException("sale not found");
        }else {
            return new SaleResponse(saleEntity.get().getId()
                    ,saleEntity.get().getSalesDate()
                    ,saleEntity.get().getSaleCode()
                    ,saleEntity.get().getCustomer(),
                    saleEntity.get().getPaymentMethod(),
                    saleEntity.get().getEmployee(),
                    saleEntity.get().getPackageOnSale(),
                    saleEntity.get().getPrice());
        }
    }
    public SaleResponse findById(int id) throws IdNotFoundException {
        Optional<SaleEntity> saleEntity= this.saleRepository.findById(id);
        if (!saleEntity.isPresent()){
            throw new IdNotFoundException("the id was nod found");
        }else{
            return new SaleResponse(saleEntity.get().getId()
                    ,saleEntity.get().getSalesDate()
                    ,saleEntity.get().getSaleCode()
                    ,saleEntity.get().getCustomer(),
                    saleEntity.get().getPaymentMethod(),
                    saleEntity.get().getEmployee(),
                    saleEntity.get().getPackageOnSale(),
                    saleEntity.get().getPrice());
        }
    }
    public List<SaleResponse> getAllSales(){
        List<SaleEntity> saleList= this.saleRepository.findAll();
        List<SaleResponse> saleResponses= saleList.stream().map(saleEntity -> {
            SaleResponse saleResponse= new SaleResponse(saleEntity.getId(),
                    saleEntity.getSalesDate(),
                    saleEntity.getSaleCode(),
                    saleEntity.getCustomer(),
                    saleEntity.getPaymentMethod(),
                    saleEntity.getEmployee(),
                    saleEntity.getPackageOnSale(),
                    saleEntity.getPrice());
            return saleResponse;
        }).collect(Collectors.toList());
        return saleResponses;
    }
    public SaleResponse deleteById(int id) throws IdNotFoundException {
        Optional<SaleEntity> sale= this.saleRepository.findById(id);
        if (!sale.isPresent()){
             throw new  IdNotFoundException("the id was not found");
        }
        else{
            this.saleRepository.delete(sale.get());
        }
        return new SaleResponse(sale.get().getId()
                ,sale.get().getSalesDate()
                ,sale.get().getSaleCode()
                ,sale.get().getCustomer(),
                sale.get().getPaymentMethod(),
                sale.get().getEmployee(),
                sale.get().getPackageOnSale(),
                sale.get().getPrice());
    }
}
