package tqs.WashNow.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import tqs.WashNow.entities.Payment;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class PaymentRepositoryIT {

    @Autowired
    private PaymentRepository paymentRepository;

    @Test
    void testSavePayment() {
        Payment payment = new Payment();
        payment.setAmount(100.0);

        Payment savedPayment = paymentRepository.save(payment);

        assertThat(savedPayment).isNotNull();
        assertThat(savedPayment.getId()).isNotNull();
        assertThat(savedPayment.getAmount()).isEqualTo(100.0);
    }

    @Test
    void testFindPaymentById() {
        Payment payment = new Payment();
        payment.setAmount(200.0);
        payment = paymentRepository.save(payment);

        Optional<Payment> retrievedPayment = paymentRepository.findById(payment.getId());

        assertThat(retrievedPayment).isPresent();
        assertThat(retrievedPayment.get().getAmount()).isEqualTo(200.0);
    }

    @Test
    void testDeletePayment() {
        Payment payment = new Payment();
        payment.setAmount(50.0);
        payment = paymentRepository.save(payment);

        paymentRepository.deleteById(payment.getId());

        Optional<Payment> deletedPayment = paymentRepository.findById(payment.getId());
        assertThat(deletedPayment).isNotPresent();
    }
}