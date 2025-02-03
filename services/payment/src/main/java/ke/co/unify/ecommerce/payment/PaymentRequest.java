package ke.co.unify.ecommerce.payment;

import ke.co.unify.ecommerce.payment.enums.PaymentMethod;

import java.math.BigDecimal;

public record PaymentRequest(
        Long id,
        BigDecimal amount,
        PaymentMethod paymentMethod,
        Long orderId,
        String orderReference,
        Customer customer

) {
}
