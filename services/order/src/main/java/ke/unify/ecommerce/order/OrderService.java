package ke.unify.ecommerce.order;

import jakarta.persistence.EntityNotFoundException;
import ke.unify.ecommerce.customer.CustomerClient;
import ke.unify.ecommerce.exception.BusinessException;
import ke.unify.ecommerce.kafka.KafkaOrderProducer;
import ke.unify.ecommerce.kafka.OrderConfirmation;
import ke.unify.ecommerce.orderline.OrderLineRequest;
import ke.unify.ecommerce.orderline.OrderLineService;
import ke.unify.ecommerce.payment.PaymentClient;
import ke.unify.ecommerce.payment.PaymentRequest;
import ke.unify.ecommerce.product.ProductClient;
import ke.unify.ecommerce.product.PurchaseRequest;
import ke.unify.ecommerce.product.PurchaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    private final OrderLineService orderLineService;

    private final CustomerClient customerClient;
    private final ProductClient productClient;

    private final KafkaOrderProducer orderProducer;
    private final PaymentClient paymentClient;

    public Long createOrder(OrderRequest request) {

        // Check if we have the customer - OpenFeign
        var customer = customerClient.getCustomerById(request.customerId()).orElseThrow(() -> new BusinessException("Customer not found with the provided ID:: " + request.customerId()));

        //purchase the product --> product ms - RestTemplate
        List<PurchaseResponse> purchaseResponses = productClient.purchaseProducts(request.products());

        // Persist the order
        var order = orderRepository.save(orderMapper.toOrder(request));

        // Persist the order lines
        for (PurchaseRequest purchaseRequest : request.products()) {
            orderLineService.saveOrderLine(
                    new OrderLineRequest(
                            null,
                            order.getId(),
                            purchaseRequest.productId(),
                            purchaseRequest.quantity()
                    )
            );
        }

        // Start payment process
        var paymentRequest = new PaymentRequest(
                request.amount(),
                request.paymentMethod(),
                order.getId(),
                order.getReference(),
                customer
        );
        paymentClient.requestOrderPayment(paymentRequest);

        //Send order confirmation to notification ms (kafka)
        orderProducer.sendOrderConfirmation(
                new OrderConfirmation(
                        request.reference(),
                        request.amount(),
                        request.paymentMethod(),
                        customer,
                        purchaseResponses
                )
        );

        return order.getId();
    }

    public List<OrderResponse> findAll() {
        return orderRepository.findAll().stream().map(orderMapper::fromOrder).collect(Collectors.toList());
    }

    public OrderResponse findById(Long id) {
        return orderRepository.findById(id).map(orderMapper::fromOrder).orElseThrow(()-> new EntityNotFoundException("Order not found with the provided ID:: " + id));
    }
}
