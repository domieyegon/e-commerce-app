package ke.co.unify.ecommerce.payment;

import jakarta.validation.Valid;
import ke.co.unify.ecommerce.notification.NotificationProducer;
import ke.co.unify.ecommerce.notification.PaymentNotificationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private final NotificationProducer notificationProducer;

    public Long createPayment(@Valid PaymentRequest request) {
        var payment = paymentRepository.save(paymentMapper.toPayment(request));

        notificationProducer.sendNotification(
                new PaymentNotificationRequest(
                request.orderReference(),
                request.amount(),
                request.paymentMethod(),
                request.customer().firstname(),
                request.customer().lastname(),
                request.customer().email()
        ));
        return payment.getId();
    }
}
