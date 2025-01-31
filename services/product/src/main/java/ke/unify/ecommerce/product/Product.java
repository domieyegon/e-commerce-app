package ke.unify.ecommerce.product;

import jakarta.persistence.*;
import ke.unify.ecommerce.category.Category;
import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private double availableQuantity;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
