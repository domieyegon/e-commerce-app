package ke.unify.ecommerce.product;

import java.math.BigDecimal;

public record PurchaseResponse(
        Long id,
        String name,
        String description,
        BigDecimal price,
        double availableQuantity,
        Long categoryId,
        String categoryName,
        String categoryDescription
) {
}
