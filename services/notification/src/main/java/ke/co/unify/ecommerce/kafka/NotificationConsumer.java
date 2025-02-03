package ke.co.unify.ecommerce.kafka;

import jakarta.mail.MessagingException;
import ke.co.unify.ecommerce.email.EmailService;
import ke.co.unify.ecommerce.notification.Notification;
import ke.co.unify.ecommerce.notification.NotificationRepository;
import ke.co.unify.ecommerce.notification.enums.NotificationType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static ke.co.unify.ecommerce.notification.enums.NotificationType.ORDER_CONFIRMATION;
import static ke.co.unify.ecommerce.notification.enums.NotificationType.PAYMENT_CONFIRMATION;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationConsumer {

    private final NotificationRepository notificationRepository;
    private final EmailService emailService;

    @KafkaListener(topics = "payment-topic")
    public void consumePaymentSuccessNotification(PaymentConfirmation paymentConfirmation) throws MessagingException {
        log.info("Consuming message from payment topic: {}", paymentConfirmation);
        notificationRepository.save(
                Notification.builder()
                        .type(PAYMENT_CONFIRMATION)
                        .notificationDate(LocalDateTime.now())
                        .paymentConfirmation(paymentConfirmation)
                        .build()
        );

        //  send email
        String customerName = paymentConfirmation.customerFirstname() + " " + paymentConfirmation.customerLastname();

        emailService.sendPaymentSuccessEmail(paymentConfirmation.customerEmail(), customerName, paymentConfirmation.amount(), paymentConfirmation.orderReference());

    }

    @KafkaListener(topics = "order-topic")
    public void consumeOrderConfirmationNotification(OrderConfirmation orderConfirmation) throws MessagingException {
        log.info("Consuming message from order-topic: {}", orderConfirmation);
        notificationRepository.save(
                Notification.builder()
                        .type(ORDER_CONFIRMATION)
                        .notificationDate(LocalDateTime.now())
                        .orderConfirmation(orderConfirmation)
                        .build()
        );

        // send email
        String customerName = orderConfirmation.customer().firstname()  + " " + orderConfirmation.customer().lastname();

        emailService.sendOrderConfirmationEmail(orderConfirmation.customer().email(), customerName, orderConfirmation.totalAmount(), orderConfirmation.orderReference(), orderConfirmation.products());

    }
}
