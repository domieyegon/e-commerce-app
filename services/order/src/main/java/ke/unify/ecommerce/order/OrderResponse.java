package ke.unify.ecommerce.order;

import ke.unify.ecommerce.order.enums.PaymentMethod;

import java.math.BigDecimal;

public record OrderResponse(
        Long id,
        String reference,
        BigDecimal amount,
        PaymentMethod paymentMethod,
        String customerId
) {
}
