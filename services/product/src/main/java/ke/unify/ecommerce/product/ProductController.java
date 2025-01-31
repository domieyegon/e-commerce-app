package ke.unify.ecommerce.product;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public ResponseEntity<Long> createProduct(@RequestBody @Valid ProductRequest request) {

        return ResponseEntity.ok().body(productService.createProduct(request));
    }

    @PostMapping("/purchase")
    public ResponseEntity<List<ProductPurchaseResponse>> purchaseProduct(List<ProductPurchaseRequest> request) {

        return ResponseEntity.ok().body(productService.purchaseProducts(request));
    }

    @GetMapping("{product-id}")
    public ResponseEntity<ProductResponse> findById(@PathVariable("product-id") Long productId) {

        return ResponseEntity.ok().body(productService.findById(productId));
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> findAll() {

        return ResponseEntity.ok().body(productService.findAll());
    }
}
