package org.githubwuzupkev.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.githubwuzupkev.exceptions.DuplicatedSkucodeFoundException;
import org.githubwuzupkev.exceptions.IdNotFoundException;
import org.githubwuzupkev.models.entities.ServiceEntity;
import org.githubwuzupkev.models.requests.ServiceRequest;
import org.githubwuzupkev.models.responses.ServiceResponse;
import org.githubwuzupkev.repositories.ServiceRepository;
import org.githubwuzupkev.tools.UsefulFunction;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class SrvcService {
    private final ServiceRepository serviceRepository;

    public ServiceResponse createService(ServiceRequest serviceRequest) throws DuplicatedSkucodeFoundException {
       if (serviceRequest == null) {
           throw new NullPointerException("the serviceRequest is null");
       }
       else{
           ServiceEntity serviceEntity = ServiceEntity.builder()
                   .name(serviceRequest.name())
                   .description(serviceRequest.description())
                   .price(serviceRequest.price())
                   .productDate(serviceRequest.productDate())
                   .typeOfService(serviceRequest.serviceType())
                   .destination(serviceRequest.destination())
                   .skuCode(UsefulFunction.generateRandomChars())
                   .build();
           Boolean isSkucodeAndNameExist= this.serviceRepository.existsServiceEntitiesBySkuCodeAndAndName(serviceEntity.getSkuCode(),serviceEntity.getName());
           if (!isSkucodeAndNameExist) {
               this.serviceRepository.save(serviceEntity);
           }else {
               throw new DuplicatedSkucodeFoundException("the skucode and name are duplicated");
           }
           return new ServiceResponse(serviceEntity.getId(),serviceEntity.getName(),serviceEntity.getSkuCode(),serviceEntity.getDescription(),serviceEntity.getDestination(),serviceEntity.getProductDate(),serviceEntity.getPrice(),serviceEntity.getTypeOfService().toString());
       }
    }
    @Transactional
    public ServiceResponse updateService(ServiceRequest serviceRequest,int id) throws IdNotFoundException {
        ServiceEntity serviceEntity =
                this.serviceRepository
                        .findById(id)
                        .orElseThrow(()-> new IdNotFoundException("the id: "+id+" was not found"));
        if (serviceRequest == null) {
            throw new NullPointerException("the serviceRequest is null");
        }else {
            serviceEntity.setName(serviceRequest.name());
            serviceEntity.setDescription(serviceRequest.description());
            serviceEntity.setPrice(serviceRequest.price());
            serviceEntity.setProductDate(serviceRequest.productDate());
            serviceEntity.setTypeOfService(serviceRequest.serviceType());
            serviceEntity.setDestination(serviceRequest.destination());
            this.serviceRepository.save(serviceEntity);
            log.info("the service is updated "+serviceEntity);
        }
        return new  ServiceResponse(serviceEntity.getId(),serviceEntity.getName(),serviceEntity.getSkuCode(),serviceEntity.getDescription(),serviceEntity.getDestination(),serviceEntity.getProductDate(),serviceEntity.getPrice(),serviceEntity.getTypeOfService().toString());
    }
    public ServiceResponse deleteService(int id) throws IdNotFoundException {
        Optional<ServiceEntity> serviceEntity= this.serviceRepository.findById(id);
        if (serviceEntity.isPresent()) {
            this.serviceRepository.delete(serviceEntity.get());
        }else {
            throw new IdNotFoundException("the id: "+id+" was not found");
        }
        return new ServiceResponse(serviceEntity.get().getId(),serviceEntity.get().getName(),
                serviceEntity.get().getSkuCode()
                ,serviceEntity.get().getDescription()
                ,serviceEntity.get().getDestination()
                ,serviceEntity.get().getProductDate()
                ,serviceEntity.get().getPrice(),
                serviceEntity.get().getTypeOfService().toString());
    }
    public ServiceResponse getGetServiceById(int id) throws IdNotFoundException {
        Optional<ServiceEntity> serviceEntity= this.serviceRepository.findById(id);
        if (!serviceEntity.isPresent()) {
            throw new IdNotFoundException("The id "+id+" was not found");
        }
        else{
            return new ServiceResponse(serviceEntity.get().getId(),serviceEntity.get().getName(),serviceEntity.get().getSkuCode(),serviceEntity.get().getDescription(),serviceEntity.get().getDestination(),serviceEntity.get().getProductDate(),serviceEntity.get().getPrice(),serviceEntity.get().getTypeOfService().toString());
        }
    }
    public ServiceResponse getServiceBySkucode(String skucode) throws IdNotFoundException {
        Optional<ServiceEntity> serviceEntity= this.serviceRepository.findServiceEntitiesBySkuCode(skucode);
        if (!serviceEntity.isPresent()){
            throw new IdNotFoundException("The skucode "+skucode+" was not found");
        }
        else {
            return new ServiceResponse(serviceEntity.get().getId(),serviceEntity.get().getName(),serviceEntity.get().getSkuCode(),serviceEntity.get().getDescription(),serviceEntity.get().getDestination(),serviceEntity.get().getProductDate(),serviceEntity.get().getPrice(),serviceEntity.get().getTypeOfService().toString());
        }
    }

    public List<ServiceResponse> getAllServices() {
        List<ServiceEntity> serviceEntities= this.serviceRepository.findAll();
        List<ServiceResponse> serviceResponses= serviceEntities.stream().map(
                customService->{
                    ServiceResponse response= new ServiceResponse(
                            customService.getId(),customService.getName(),
                            customService.getSkuCode(),customService.getDescription(),
                            customService.getDestination(),
                            customService.getProductDate()
                            ,customService.getPrice(),
                            customService.getTypeOfService().toString()
                    );
                    return response;
                }
        ).collect(Collectors.toList());
        return serviceResponses;
    }

    public ServiceResponse deleteServiceBySkucode(String skucode) throws IdNotFoundException {
        Optional<ServiceEntity> serviceEntity= this.serviceRepository.findServiceEntitiesBySkuCode(skucode);
        if (serviceEntity.isPresent()) {
            this.serviceRepository.delete(serviceEntity.get());
        }else {
            throw new IdNotFoundException("the id: "+skucode+" was not found");
        }
        return new ServiceResponse(serviceEntity.get().getId(),serviceEntity.get().getName(),
                serviceEntity.get().getSkuCode()
                ,serviceEntity.get().getDescription()
                ,serviceEntity.get().getDestination()
                ,serviceEntity.get().getProductDate()
                ,serviceEntity.get().getPrice(),
                serviceEntity.get().getTypeOfService().toString());
    }
}
