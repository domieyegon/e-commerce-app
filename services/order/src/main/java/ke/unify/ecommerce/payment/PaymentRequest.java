package ke.unify.ecommerce.payment;

import ke.unify.ecommerce.customer.CustomerResponse;
import ke.unify.ecommerce.order.enums.PaymentMethod;

import java.math.BigDecimal;

public record PaymentRequest(
    BigDecimal amount,
        PaymentMethod paymentMethod,
        Long orderId,
        String orderReference,
        CustomerResponse customer
) {
}
