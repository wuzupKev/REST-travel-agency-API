package org.githubwuzupkev.controllers;

import org.githubwuzupkev.exceptions.DuplicatedUserFoundException;
import org.githubwuzupkev.exceptions.IdNotFoundException;
import org.githubwuzupkev.models.requests.CustomerRequest;
import org.githubwuzupkev.models.requests.SaleRequest;
import org.githubwuzupkev.models.responses.CustomerResponse;
import org.githubwuzupkev.models.responses.SaleResponse;
import org.githubwuzupkev.services.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sale")
public class SaleController {
    @Autowired
    private SaleService saleService;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<SaleResponse> createSale(@RequestBody SaleRequest request) throws  IdNotFoundException {
        return ResponseEntity.ok(this.saleService.createASale(request));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<SaleResponse>> getAllSale() {
        return ResponseEntity.ok(this.saleService.getAllSales());
    }

    @GetMapping("/id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<SaleResponse> getServiceById(@PathVariable int id) throws IdNotFoundException {
        return ResponseEntity.ok(this.saleService.findById(id));
    }

    @GetMapping("/sales-code/{skucode}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<SaleResponse> getSaleBySkucode(@PathVariable String skucode) throws IdNotFoundException {
        return ResponseEntity.ok(this.saleService.findByCode(skucode));
    }

    @DeleteMapping("/delete-sale/{skucode}")
    public ResponseEntity<SaleResponse> deleteService(@PathVariable String skucode) throws IdNotFoundException {
        return ResponseEntity.ok(this.saleService.deleteByIdSale(skucode));
    }

    @PutMapping("/update-sale/{id}")
    public ResponseEntity<SaleResponse> updateService(@PathVariable int id,@RequestBody SaleRequest saleRequest) throws IdNotFoundException {
        return ResponseEntity.ok(this.saleService.UpdateASale(saleRequest, id));
    }

}
