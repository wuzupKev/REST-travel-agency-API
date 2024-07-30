package org.githubwuzupkev.controllers;

import org.githubwuzupkev.exceptions.DuplicatedSkucodeFoundException;
import org.githubwuzupkev.exceptions.DuplicatedUserFoundException;
import org.githubwuzupkev.exceptions.IdNotFoundException;
import org.githubwuzupkev.models.requests.CustomerRequest;
import org.githubwuzupkev.models.requests.ServiceRequest;
import org.githubwuzupkev.models.responses.CustomerResponse;
import org.githubwuzupkev.models.responses.ServiceResponse;
import org.githubwuzupkev.repositories.ServiceRepository;
import org.githubwuzupkev.services.SrvcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/services")
public class ServiceController {
    @Autowired
    private SrvcService srvcService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ServiceResponse> createService(@RequestBody ServiceRequest serviceRequest) throws DuplicatedSkucodeFoundException {
        return ResponseEntity.ok(srvcService.createService(serviceRequest));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ServiceResponse>> getAllServices() {
        return ResponseEntity.ok(srvcService.getAllServices());
    }

    @GetMapping("/id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ServiceResponse> getServiceById(@PathVariable int id) throws IdNotFoundException {
        return ResponseEntity.ok(srvcService.getGetServiceById(id));
    }

    @GetMapping("/sku-code/{skucode}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ServiceResponse> getServiceBySkucode(@PathVariable String skucode) throws IdNotFoundException {
        return ResponseEntity.ok(srvcService.getServiceBySkucode(skucode));
    }

    @DeleteMapping("/delete-service/{skucode}")
    public ResponseEntity<ServiceResponse> deleteService(@PathVariable String skucode) throws IdNotFoundException {
        return ResponseEntity.ok(this.srvcService.deleteServiceBySkucode(skucode));
    }

    @PutMapping("/update-service/{id}")
    public ResponseEntity<ServiceResponse> updateService(@PathVariable int id,@RequestBody ServiceRequest serviceRequest) throws IdNotFoundException {
        return ResponseEntity.ok(this.srvcService.updateService(serviceRequest,id));
    }
}
