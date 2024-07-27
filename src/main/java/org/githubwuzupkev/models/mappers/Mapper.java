package org.githubwuzupkev.models.mappers;

import org.githubwuzupkev.models.entities.PackageEntity;
import org.githubwuzupkev.models.entities.ServiceEntity;
import org.githubwuzupkev.models.requests.PackageRequest;
import org.githubwuzupkev.models.requests.ServiceRequest;
import org.springframework.stereotype.Service;

import java.util.Set;
@Service
public class Mapper {
    public ServiceEntity toServiceEntity(ServiceRequest serviceRequest) {
        ServiceEntity entity= ServiceEntity.builder()
                .name(serviceRequest.name())
                .description(serviceRequest.description())
                .price(serviceRequest.price())
                .build();
        return entity;
    }
}
