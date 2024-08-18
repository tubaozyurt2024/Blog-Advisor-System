package com.patika.bloghubservice.client.payment.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PaymentRequest {

    private BigDecimal amount;
    private String email;

    public PaymentRequest(double v, String email) {
        this.amount = BigDecimal.valueOf(v);
        this.email = email;
    }
}
