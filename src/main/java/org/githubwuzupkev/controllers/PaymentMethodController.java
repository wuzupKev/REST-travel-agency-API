package org.githubwuzupkev.controllers;

import org.githubwuzupkev.exceptions.DuplicatedSkucodeFoundException;
import org.githubwuzupkev.exceptions.IdNotFoundException;
import org.githubwuzupkev.models.requests.PaymentMethodRequest;
import org.githubwuzupkev.models.requests.ServiceRequest;
import org.githubwuzupkev.models.responses.PaymentMethodResponse;
import org.githubwuzupkev.models.responses.ServiceResponse;
import org.githubwuzupkev.services.PaymentMethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/payment-method")
public class PaymentMethodController {
    @Autowired
    private PaymentMethodService paymentMethodService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<PaymentMethodResponse> createPayMethod(@RequestBody PaymentMethodRequest paymentMethodRequest) throws DuplicatedSkucodeFoundException {
        return ResponseEntity.ok(this.paymentMethodService.createPaymentMethod(paymentMethodRequest));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<PaymentMethodResponse>> getAllPaymentMethod() {
        return ResponseEntity.ok(paymentMethodService.getAllPaymentMethod());
    }

    @GetMapping("/id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<PaymentMethodResponse> getPaymentById(@PathVariable int id) throws IdNotFoundException {
        return ResponseEntity.ok(this.paymentMethodService.getById(id));
    }

    @GetMapping("/method-name/{name}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<PaymentMethodResponse> getPaymethodByName(@PathVariable String name) throws IdNotFoundException {
        return ResponseEntity.ok(this.paymentMethodService.getByPaymentName(name));
    }

    @DeleteMapping("/delete-paymethod/{id}")
    public ResponseEntity<PaymentMethodResponse> deletePaymethod(@PathVariable int id) throws IdNotFoundException {
        return ResponseEntity.ok(this.paymentMethodService.delete(id));
    }

    @PutMapping("/update-payment-method/{id}")
    public ResponseEntity<PaymentMethodResponse> updateService(@PathVariable int id,@RequestBody PaymentMethodRequest paymentMethodRequest) throws IdNotFoundException {
        return ResponseEntity.ok(this.paymentMethodService.updatePaymentMethod(paymentMethodRequest,id));
    }
}
