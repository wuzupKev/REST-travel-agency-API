package org.githubwuzupkev.services;

import org.githubwuzupkev.exceptions.DuplicatedSkucodeFoundException;
import org.githubwuzupkev.exceptions.IdNotFoundException;
import org.githubwuzupkev.models.entities.PackageEntity;
import org.githubwuzupkev.models.entities.ServiceEntity;
import org.githubwuzupkev.models.entities.ServiceEnum;
import org.githubwuzupkev.models.mappers.Mapper;
import org.githubwuzupkev.models.requests.PackageRequest;
import org.githubwuzupkev.models.requests.ServiceRequest;
import org.githubwuzupkev.repositories.PackageRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.Provider;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class PackageServiceTest {
    @Autowired
    private PackageService packageService;
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
        ServiceRequest serviceRequest=
                new ServiceRequest("Renta por noche","Coche listo para usar",
                        "Villa mar, venezuela",new Date(System.currentTimeMillis()).toString(),
                        30.99, ServiceEnum.ALQUILER_DE_AUTO);
        ServiceEntity serviceEntity= ServiceEntity.builder()
                .name("Alquiler de auto por hora")
                .typeOfService(ServiceEnum.ALQUILER_DE_AUTO)
                .description("Coche listo para usar")
                .price(45.55)
                .destination("Coche listo para usarRenta por noche")
                .productDate("23-44-55")
                .build();
        System.out.println(this.packageService.createPackage(new PackageRequest("Pack bergas",Set.of(serviceEntity))));
    }

    @Test
    void update() throws IdNotFoundException {
        ServiceEntity serviceEntity3= ServiceEntity.builder()
                .name("titulo random")
                .typeOfService(ServiceEnum.HOTEL_POR_NOCHE)
                .description("sdsd")
                .price(134.44)
                .destination("por nochcscscee")
                .productDate("23-44-55")
                .build();
        ServiceEntity serviceEntity= ServiceEntity.builder()
                .name("random ")
                .typeOfService(ServiceEnum.PASAJE_DE_AVION)
                .description("descripcion random")
                .price(233.44)
                .destination("destino random")
                .productDate("23-44-55")
                .build();

        this.packageService.updatePackage(new PackageRequest("Titulo random",Set.of(serviceEntity3,serviceEntity)),6);
    }
}