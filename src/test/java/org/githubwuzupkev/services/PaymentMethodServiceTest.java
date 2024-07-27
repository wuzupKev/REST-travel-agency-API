package org.githubwuzupkev.services;

import org.githubwuzupkev.exceptions.IdNotFoundException;
import org.githubwuzupkev.models.requests.PaymentMethodRequest;
import org.githubwuzupkev.repositories.PaymentMethodRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class PaymentMethodServiceTest {
    @Autowired
    private PaymentMethodService paymentMethodService;
    @Test
    void createPaymentMethod(){
        PaymentMethodRequest paymentMethodRequest= new PaymentMethodRequest("Paypal");
        paymentMethodService.createPaymentMethod(paymentMethodRequest);
    }

    @Test
    void getAll() throws IdNotFoundException {
        System.out.println(this.paymentMethodService.delete(2));
    }
}