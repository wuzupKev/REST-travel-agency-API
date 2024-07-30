package org.githubwuzupkev.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.githubwuzupkev.exceptions.IdNotFoundException;
import org.githubwuzupkev.models.entities.PaymentMethodEntity;
import org.githubwuzupkev.models.requests.PaymentMethodRequest;
import org.githubwuzupkev.models.responses.PaymentMethodResponse;
import org.githubwuzupkev.repositories.PaymentMethodRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class PaymentMethodService {
    private final PaymentMethodRepository methodRepository;
    public PaymentMethodResponse createPaymentMethod(PaymentMethodRequest paymentMethodRequest){
        Boolean existMethod= this.methodRepository.existsPaymentMethodEntitiesByNameIgnoreCase(paymentMethodRequest.name());
        if (existMethod){
            throw new NullPointerException("The method's pay does exist, plz create a new one");
        }
        else {
            PaymentMethodEntity paymentMethod= PaymentMethodEntity.builder()
                    .name(paymentMethodRequest.name())
                    .build();
            this.methodRepository.save(paymentMethod);
            return new PaymentMethodResponse(paymentMethod.getId(), paymentMethod.getName());
        }
    }
    @Transactional
    public PaymentMethodResponse updatePaymentMethod(PaymentMethodRequest paymentMethodRequest,int id) throws IdNotFoundException {
        Optional<PaymentMethodEntity> existPaymentMethod=this.methodRepository.findById(id);
        if (!existPaymentMethod.isPresent()){
            throw new IdNotFoundException("The id: "+id+"doesnt exist");
        }else {
            PaymentMethodEntity updatedPaymentMethod= existPaymentMethod.get();
            updatedPaymentMethod.setName(paymentMethodRequest.name());
            this.methodRepository.save(updatedPaymentMethod);
            return new PaymentMethodResponse(updatedPaymentMethod.getId(), updatedPaymentMethod.getName());
        }
    }
    public List<PaymentMethodResponse> getAllPaymentMethod() {
        List<PaymentMethodEntity> paymentMethodEntities = this.methodRepository.findAll();
        List<PaymentMethodResponse> paymentMethodResponses = paymentMethodEntities.stream().map(
                paymentMethod -> {
                    PaymentMethodResponse paymentMethodResponse = new
                            PaymentMethodResponse(paymentMethod.getId(),
                            paymentMethod.getName());
                    return paymentMethodResponse;
                }
        ).collect(Collectors.toList());
        return paymentMethodResponses;
    }

    public PaymentMethodResponse getById(int id) throws IdNotFoundException {
        Optional<PaymentMethodEntity> existPaymentMethod= this.methodRepository.findById(id);
        if (!existPaymentMethod.isPresent()){
            throw new IdNotFoundException("the id: "+id+" doesnot exist , plz try new one");
        }
        else{
            return new  PaymentMethodResponse(existPaymentMethod.get().getId(),existPaymentMethod.get().getName());
        }
    }

    public PaymentMethodResponse getByPaymentName(String name){
        Optional<PaymentMethodEntity> paymentMethod= this.methodRepository.findPaymentMethodEntitiesByNameIgnoreCase(name);
        if(!paymentMethod.isPresent()){
            throw new NullPointerException("The payment method: "+name+" doesnt exist");
        }else {
            return new PaymentMethodResponse(paymentMethod.get().getId(),paymentMethod.get().getName());
        }
    }

    public PaymentMethodResponse delete(int id){
        Optional<PaymentMethodEntity> existPaymentMethod=this.methodRepository.findById(id);
        if (!existPaymentMethod.isPresent()){
            throw new NullPointerException("The id: "+id+" doesnot exist , plz try new one");
        }
        else {
            this.methodRepository.delete(existPaymentMethod.get());
            log.info("the user "+existPaymentMethod.get()+" has been deleted");
            return new PaymentMethodResponse(existPaymentMethod.get().getId(),existPaymentMethod.get().getName());
        }
    }
}
