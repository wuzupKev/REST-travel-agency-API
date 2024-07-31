package org.githubwuzupkev.services;

import org.githubwuzupkev.exceptions.DuplicatedSkucodeFoundException;
import org.githubwuzupkev.models.enums.ServiceEnum;
import org.githubwuzupkev.models.requests.ServiceRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SrvcServiceTest {
    @Autowired
    private SrvcService service;
    @Test
    void createService() throws DuplicatedSkucodeFoundException {
        ServiceRequest serviceRequest= new ServiceRequest("Hotel por noche","se arrienda hotel para un a persona","Panama city, Panama",null    ,54.55, ServiceEnum.HOTEL_POR_NOCHE);
        this.service.createService(serviceRequest);
    }
}