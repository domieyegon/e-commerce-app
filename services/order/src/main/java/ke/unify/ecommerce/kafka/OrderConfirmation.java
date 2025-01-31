package ke.unify.ecommerce.kafka;

import ke.unify.ecommerce.customer.CustomerResponse;
import ke.unify.ecommerce.order.enums.PaymentMethod;
import ke.unify.ecommerce.product.PurchaseResponse;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation(
        String orderReference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        CustomerResponse customer,
        List<PurchaseResponse> products
) {
}
