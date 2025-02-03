package ke.co.unify.ecommerce.kafka;

import java.math.BigDecimal;

public record Product(
        Long id,
        String name,
        String description,
        BigDecimal price,
        double quantity
) {
}
