package org.githubwuzupkev.controllers;
import org.githubwuzupkev.exceptions.IdNotFoundException;
import org.githubwuzupkev.models.requests.PackageRequest;
import org.githubwuzupkev.models.responses.PackageResponse;
import org.githubwuzupkev.services.PackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/packages")
public class PackageController {
    @Autowired
    private PackageService packageService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<PackageResponse> createPackage(@RequestBody PackageRequest packageRequest)  {
        return ResponseEntity.ok(this.packageService.createPackage(packageRequest));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<PackageResponse>> getAllPacks() {
        return ResponseEntity.ok(this.packageService.getAllPackages());
    }

    @GetMapping("/id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<PackageResponse> getPackById(@PathVariable int id) throws IdNotFoundException {
        return ResponseEntity.ok(this.packageService.getPackageByid(id));
    }

    @GetMapping("/sku-code/{skucode}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<PackageResponse> getPackBySkucode(@PathVariable String skucode) throws IdNotFoundException {
        return ResponseEntity.ok(this.packageService.getPackageBySkuCode(skucode));
    }

    @DeleteMapping("/delete-package/{skucode}")
    public ResponseEntity<PackageResponse> deletePack(@PathVariable String skucode) throws IdNotFoundException {
        return ResponseEntity.ok(this.packageService.deletePackageBySkucode(skucode));
    }

    @PutMapping("/update-pack/{id}")
    public ResponseEntity<PackageResponse> updatePack(@PathVariable int id,@RequestBody PackageRequest packageRequest) throws IdNotFoundException {
        return ResponseEntity.ok(this.packageService.updatePackage(packageRequest,id));
    }
}
