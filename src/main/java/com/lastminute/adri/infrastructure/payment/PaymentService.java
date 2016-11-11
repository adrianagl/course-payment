package com.lastminute.adri.infrastructure.payment;

import java.math.BigDecimal;

public interface PaymentService {
    boolean makePayment(BigDecimal coursePrice);
}
