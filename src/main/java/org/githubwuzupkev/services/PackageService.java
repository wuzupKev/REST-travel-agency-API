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

    public PackageResponse createPackage(PackageRequest packageRequest) throws DuplicatedSkucodeFoundException {
      if (packageRequest == null) {
          throw  new NullPointerException("packageRequest is null");
     }else {
            PackageEntity packageEntity =PackageEntity.builder()
                    .title(packageRequest.title())
                    .skuCode(UsefulFunction.generateRandomChars())
                    .services(packageRequest.services().stream().collect(Collectors.toSet()))
                    .price(packageRequest.services().stream().mapToDouble(ServiceEntity::getPrice).sum())
                    .build();
            Boolean isSkuCodeRegistered= this.packageRepository.existsPackageEntitiesBySkuCode(packageEntity.getSkuCode());
            if (!isSkuCodeRegistered){
                this.packageRepository.save(packageEntity);
            }else {
                throw new DuplicatedSkucodeFoundException("Duplicated skucode found on "+packageEntity.getSkuCode());
            }
            return new PackageResponse(packageEntity.getId(),packageEntity.getTitle(),packageEntity.getSkuCode(),packageEntity.getServices().stream().toList(),packageEntity.getPrice());
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
            packageEntity.setTitle(packageRequest.title());
            packageEntity.setServices(new HashSet<>(packageRequest.services()));
            packageEntity.setPrice(packageRequest.services().stream().mapToDouble(ServiceEntity::getPrice).sum());
            packageEntity.setSkuCode(packageEntity.getSkuCode());
            this.packageRepository.save(packageEntity);
            log.info("the pack with id: "+packageEntity.getId()+" has been updated");
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
}
