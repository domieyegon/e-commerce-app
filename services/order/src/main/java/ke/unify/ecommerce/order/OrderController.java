package ke.unify.ecommerce.order;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<Long> createOrder(@RequestBody @Valid OrderRequest request) {

        return ResponseEntity.ok().body(orderService.createOrder(request));
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAllOrders() {

        return ResponseEntity.ok().body(orderService.findAll());
    }

    @GetMapping("{order-id}")
    public ResponseEntity<OrderResponse> findOrderById(@PathVariable("order-id") Long orderId) {

        return ResponseEntity.ok().body(orderService.findById(orderId));
    }
}
