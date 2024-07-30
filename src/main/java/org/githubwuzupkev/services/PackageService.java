package org.githubwuzupkev.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.githubwuzupkev.exceptions.DuplicatedSkucodeFoundException;
import org.githubwuzupkev.exceptions.IdNotFoundException;
import org.githubwuzupkev.models.entities.PackageEntity;
import org.githubwuzupkev.models.entities.ServiceEntity;
import org.githubwuzupkev.models.requests.PackageRequest;
import org.githubwuzupkev.models.responses.PackageResponse;
import org.githubwuzupkev.repositories.PackageRepository;
import org.githubwuzupkev.repositories.ServiceRepository;
import org.githubwuzupkev.tools.UsefulFunction;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class PackageService {
    private final PackageRepository packageRepository;
    private final ServiceRepository serviceRepository;
    public PackageResponse createPackage(PackageRequest packageRequest) {
        if(packageRequest==null){
            throw new NullPointerException("the package is null");
        }
        else {
            Set<ServiceEntity> services = new HashSet<>();
            for (String skuCode : packageRequest.services()) {
                ServiceEntity s = this.serviceRepository
                        .findServiceEntitiesBySkuCode(skuCode)
                        .orElseThrow(() -> new RuntimeException("Service with SKU code " + skuCode + " not found"));
                services.add(s);
            }

            PackageEntity packageEntity = PackageEntity.builder()
                    .price(services.stream().mapToDouble(ServiceEntity::getPrice).sum())
                    .title(packageRequest.title())
                    .services(services)
                    .skuCode(UsefulFunction.generateRandomChars())
                    .build();
            this.packageRepository.save(packageEntity);
            log.info("Created package {}", packageEntity);
            return new PackageResponse(packageEntity.getId(),
                    packageEntity.getTitle(),packageEntity.getSkuCode(),
                    packageEntity.getServices().stream().toList(),packageEntity.getPrice());
        }
    }

    @Transactional
    public PackageResponse updatePackage(PackageRequest packageRequest,int id) throws IdNotFoundException {
        PackageEntity packageEntity =
                this.packageRepository.findById(id)
                        .orElseThrow(()->new IdNotFoundException("the id :"+id+" was not found"));
        if (packageRequest == null) {
            throw  new NullPointerException("packageRequest is null");
        }
        else {
            Set<ServiceEntity> services = new HashSet<>();
            for (String skuCode : packageRequest.services()) {
                ServiceEntity s = this.serviceRepository
                        .findServiceEntitiesBySkuCode(skuCode)
                        .orElseThrow(() -> new RuntimeException("Service with SKU code " + skuCode + " not found"));
                services.add(s);
            }
            packageEntity.setServices(services);
            packageEntity.setSkuCode(packageEntity.getSkuCode());
            packageEntity.setTitle(packageRequest.title());
            packageEntity.setPrice(services.stream().mapToDouble(ServiceEntity::getPrice).sum());
            this.packageRepository.save(packageEntity);
            log.info("Updated package {}", packageEntity);
        }

        return new PackageResponse(packageEntity.getId(),packageEntity.getTitle(),packageEntity.getSkuCode(),packageEntity.getServices().stream().toList(),packageEntity.getPrice());
    }

    public PackageResponse deletePackage(int id) throws IdNotFoundException {
        Optional<PackageEntity> packageEntity= this.packageRepository.findById(id);
        if (!packageEntity.isPresent()) {
            throw new IdNotFoundException("the id :"+id+" was not found");
        }
        PackageEntity packageEntityToDelete = packageEntity.get();
        this.packageRepository.deleteById(id);
        return new  PackageResponse(packageEntityToDelete.getId(),packageEntityToDelete.getTitle(),
                packageEntityToDelete.getSkuCode()
                ,packageEntityToDelete.getServices().stream().toList()
                ,packageEntityToDelete.getPrice());
    }

    public PackageResponse getPackageByid(int id) throws IdNotFoundException {
        Optional<PackageEntity> packageEntity= this.packageRepository.findById(id);
        if (!packageEntity.isPresent()) {
            throw new IdNotFoundException("the id :"+id+" was not found");
        }
        PackageEntity packageEntityToReturn = packageEntity.get();
        return new  PackageResponse(packageEntityToReturn.getId(),packageEntityToReturn.getTitle(),packageEntityToReturn.getSkuCode(),packageEntityToReturn.getServices().stream().toList(),packageEntityToReturn.getPrice());
    }

    public PackageResponse getPackageBySkuCode(String skuCode) throws IdNotFoundException {
        Optional<PackageEntity> packageEntity= this.packageRepository.findPackageEntityBySkuCode(skuCode);
        if (!packageEntity.isPresent()) {
            throw new IdNotFoundException("the skuCode :"+skuCode+" was not found");
        }
        PackageEntity packageEntityToReturn = packageEntity.get();
        return new PackageResponse(packageEntityToReturn.getId(),packageEntityToReturn.getTitle(),packageEntityToReturn.getSkuCode(),packageEntityToReturn.getServices().stream().toList(),packageEntityToReturn.getPrice());
    }

    public List<PackageResponse> getAllPackages(){
        List<PackageEntity> packageEntityList= this.packageRepository.findAll();
        List<PackageResponse> packageResponseList=packageEntityList.stream().map(
            packageEntity -> {
                PackageResponse response =
                        new PackageResponse(packageEntity.getId(),packageEntity.getTitle()
                                ,packageEntity.getSkuCode()
                                ,packageEntity.getServices().stream().toList(),packageEntity.getPrice());
                return response;
            }
        ).collect(Collectors.toList());
        return packageResponseList;
    }

    public PackageResponse deletePackageBySkucode(String skucode) throws IdNotFoundException {
        Optional<PackageEntity> packageEntity= this.packageRepository.findPackageEntityBySkuCode(skucode);
        if (!packageEntity.isPresent()) {
            throw new IdNotFoundException("the id :"+skucode+" was not found");
        }
        PackageEntity packageEntityToDelete = packageEntity.get();
        this.packageRepository.delete(packageEntityToDelete);
        return new  PackageResponse(packageEntityToDelete.getId(),packageEntityToDelete.getTitle(),
                packageEntityToDelete.getSkuCode()
                ,packageEntityToDelete.getServices().stream().toList()
                ,packageEntityToDelete.getPrice());
    }

}
