package org.githubwuzupkev.services;

import org.githubwuzupkev.exceptions.DuplicatedSkucodeFoundException;
import org.githubwuzupkev.models.entities.ServiceEntity;
import org.githubwuzupkev.models.entities.ServiceEnum;
import org.githubwuzupkev.models.requests.ServiceRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class SrvcServiceTest {
    @Autowired
    private SrvcService service;
    @Test
    void createService() throws DuplicatedSkucodeFoundException {
        ServiceRequest serviceRequest= new ServiceRequest("Alquiler de auto","se alquila auto usado","Playa bonita",null,34.55, ServiceEnum.ALQUILER_DE_AUTO);
        this.service.createService(serviceRequest);
    }
}