package ke.co.unify.ecommerce.kafka;

import ke.co.unify.ecommerce.kafka.enums.PaymentMethod;

import java.math.BigDecimal;

public record PaymentConfirmation (
        String orderReference,
        BigDecimal amount,
        PaymentMethod paymentMethod,
        String customerFirstname,
        String customerLastname,
        String customerEmail
) {
}
