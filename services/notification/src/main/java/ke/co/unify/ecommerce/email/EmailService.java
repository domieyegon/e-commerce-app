package ke.co.unify.ecommerce.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import ke.co.unify.ecommerce.kafka.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.util.encoders.UTF8;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;
import static ke.co.unify.ecommerce.email.EmailTemplates.ORDER_CONFIRMATION;
import static ke.co.unify.ecommerce.email.EmailTemplates.PAYMENT_CONFIRMATION;
import static org.springframework.mail.javamail.MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    @Async
    public void sendPaymentSuccessEmail(String destinationEmail, String customerName, BigDecimal amount, String orderReference) throws MessagingException {

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, MULTIPART_MODE_MIXED_RELATED, UTF_8.name());
        helper.setFrom("contact@unify.co.ke");
        final String templateName = PAYMENT_CONFIRMATION.getTemplate();
        Map<String, Object> variables = new HashMap<>();
        variables.put("customerName", customerName);
        variables.put("amount", amount);
        variables.put("orderReference", orderReference);

        Context context = new Context();
        context.setVariables(variables);

        helper.setSubject(PAYMENT_CONFIRMATION.getSubject());

        try {
            String htmlTemplate = templateEngine.process(templateName, context);
            helper.setText(htmlTemplate, true);

            helper.setTo(destinationEmail);
            mailSender.send(message);
            log.info("Email sent successfully to: {} with template: {}", destinationEmail, htmlTemplate);
        } catch (MessagingException e) {
            log.warn("WARNING - Cannot send email to {}", destinationEmail);
        }

    }
    @Async
    public void sendOrderConfirmationEmail(String destinationEmail, String customerName, BigDecimal amount, String orderReference, List<Product> products) throws MessagingException {

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, MULTIPART_MODE_MIXED_RELATED, UTF_8.name());
        helper.setFrom("contact@unify.co.ke");
        final String templateName = ORDER_CONFIRMATION.getTemplate();
        Map<String, Object> variables = new HashMap<>();
        variables.put("customerName", customerName);
        variables.put("amount", amount);
        variables.put("orderReference", orderReference);
        variables.put("products", products);

        Context context = new Context();
        context.setVariables(variables);

        helper.setSubject(ORDER_CONFIRMATION.getSubject());

        try {
            String htmlTemplate = templateEngine.process(templateName, context);
            helper.setText(htmlTemplate, true);

            helper.setTo(destinationEmail);
            mailSender.send(message);
            log.info("Email sent successfully to: {} with template: {}", destinationEmail, htmlTemplate);
        } catch (MessagingException e) {
            log.warn("WARNING - Cannot send email to {}", destinationEmail);
        }

    }
}
