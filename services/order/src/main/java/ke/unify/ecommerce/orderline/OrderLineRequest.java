package ke.unify.ecommerce.orderline;

public record OrderLineRequest(
        Long id,
        Long orderId,
        Long productId,
        double quantity
) { }
