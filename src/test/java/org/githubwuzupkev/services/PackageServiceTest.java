package org.githubwuzupkev.services;

import org.githubwuzupkev.exceptions.DuplicatedSkucodeFoundException;
import org.githubwuzupkev.exceptions.IdNotFoundException;
import org.githubwuzupkev.models.mappers.Mapper;
import org.githubwuzupkev.models.requests.PackageRequest;
import org.githubwuzupkev.repositories.PackageRepository;
import org.githubwuzupkev.repositories.ServiceRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

@SpringBootTest
class PackageServiceTest {
    @Autowired
    private PackageService packageService;
    @Autowired
    private ServiceRepository serviceRepository;
    @Autowired
    private PackageRepository packageRepository;
    @Autowired
    private Mapper mapper;
    @Test
    void getAllPackages() {}

    @Test
    void getPackageById() {}

    @Test
    void getPackageBySkucode() {}

    @Test
    void createPackage() throws DuplicatedSkucodeFoundException {
        PackageRequest packageRequest= new PackageRequest(
               "Pack de de motel todo incluido",
                Set.of("RVV63ZCO","WJQ5D1X4")
        );
        this.packageService.createPackage(packageRequest);
    }

    @Test
    void update() throws IdNotFoundException {
        PackageRequest packageRequest= new PackageRequest(
                "Pack de de hotel todo incluido",
                Set.of("RVV63ZCO","WJQ5D1X4")
        );

        this.packageService.updatePackage(packageRequest,1);
    }
}