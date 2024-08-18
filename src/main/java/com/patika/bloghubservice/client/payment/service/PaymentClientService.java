package com.patika.bloghubservice.client.payment.service;

import com.patika.bloghubservice.client.payment.dto.request.PaymentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
public class PaymentClientService {
    private final PaymentClient paymentClient;
    public void createPayment(@RequestBody PaymentRequest request){
        paymentClient.createPayment(request);
    }
}
