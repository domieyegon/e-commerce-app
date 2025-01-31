package ke.unify.ecommerce.orderline;

import ke.unify.ecommerce.order.OrderLine;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderLineService {
    private final OrderLineRepository orderLineRepository;
    private final OrderLineMapper orderLineMapper;

    public Long saveOrderLine(OrderLineRequest request) {
        var orderLine = orderLineMapper.toOrderLine(request);
        return orderLineRepository.save(orderLine).getId();
    }

    public List<OrderLineResponse> findAllByOrderId(Long orderId) {
        return orderLineRepository.findAllByOrderId(orderId).stream().map(orderLineMapper::fromOrderLine).collect(Collectors.toList());
    }
}
