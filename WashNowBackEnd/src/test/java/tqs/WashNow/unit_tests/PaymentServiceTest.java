package tqs.WashNow.unit_tests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import tqs.WashNow.entities.Payment;
import tqs.WashNow.entities.PaymentStatus;
import tqs.WashNow.repositories.PaymentRepository;
import tqs.WashNow.services.PaymentService;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @Mock
    private PaymentRepository repository;

    @InjectMocks
    private PaymentService service;

    private Payment payment;

    @BeforeEach
    void setup() {
        payment = new Payment(
            1L,
            50L,
            20.0,
            PaymentStatus.AUTHORIZED,
            LocalDateTime.of(2025, 5, 10, 12, 30)
        );
    }

    @Test
    void whenCreate_thenSaveAndReturn() {
        Payment toCreate = new Payment(
            null,
            60L,
            35.5,
            PaymentStatus.CAPTURED,
            LocalDateTime.of(2025, 5, 11, 14, 0)
        );
        Payment saved = new Payment(
            2L,
            60L,
            35.5,
            PaymentStatus.CAPTURED,
            toCreate.getTimestamp()
        );

        when(repository.save(toCreate)).thenReturn(saved);

        Payment result = service.createPayment(toCreate);

        assertNotNull(result);
        assertEquals(2L, result.getId());
        assertEquals(PaymentStatus.CAPTURED, result.getPaymentStatus());
        verify(repository, times(1)).save(toCreate);
    }

    @Test
    void whenGetByIdExists_thenReturnPayment() {
        when(repository.findById(1L)).thenReturn(Optional.of(payment));

        Payment result = service.getPaymentById(1L);

        assertNotNull(result);
        assertEquals(50L, result.getWashSessionId());
        assertEquals(20.0, result.getAmount());
        assertEquals(PaymentStatus.AUTHORIZED, result.getPaymentStatus());
        verify(repository).findById(1L);
    }

    @Test
    void whenGetByIdNotExists_thenReturnNull() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        Payment result = service.getPaymentById(99L);

        assertNull(result);
        verify(repository).findById(99L);
    }

    @Test
    void whenUpdateExists_thenSetIdAndSave() {
        Long id = 5L;
        Payment incoming = new Payment(
            null,
            70L,
            40.0,
            PaymentStatus.CAPTURED,
            LocalDateTime.of(2025, 5, 12, 9, 15)
        );
        Payment updated = new Payment(
            id,
            70L,
            40.0,
            PaymentStatus.CAPTURED,
            incoming.getTimestamp()
        );

        when(repository.existsById(id)).thenReturn(true);
        when(repository.save(any(Payment.class))).thenReturn(updated);

        Payment result = service.updatePaymentById(id, incoming);

        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals(70L, result.getWashSessionId());

        ArgumentCaptor<Payment> captor = ArgumentCaptor.forClass(Payment.class);
        verify(repository).save(captor.capture());
        assertEquals(id, captor.getValue().getId());
        assertEquals(40.0, captor.getValue().getAmount());
    }

    @Test
    void whenUpdateNotExists_thenReturnNullAndDontSave() {
        Long id = 100L;
        Payment incoming = new Payment(
            null,
            80L,
            55.0,
            PaymentStatus.PAYMENT_FAILED,
            LocalDateTime.now()
        );

        when(repository.existsById(id)).thenReturn(false);

        Payment result = service.updatePaymentById(id, incoming);

        assertNull(result);
        verify(repository, never()).save(any());
    }

    @Test
    void whenDelete_thenRepositoryDeleteById() {
        service.deletePaymentById(3L);
        verify(repository, times(1)).deleteById(3L);
    }

    @Test
    void whenGetAll_thenReturnListOfPayments() {
        Payment p1 = new Payment(1L, 10L, 15.0, PaymentStatus.CAPTURED, LocalDateTime.now().minusDays(1));
        Payment p2 = new Payment(2L, 11L, 25.0, PaymentStatus.PAYMENT_FAILED, LocalDateTime.now());
        List<Payment> list = Arrays.asList(p1, p2);

        when(repository.findAll()).thenReturn(list);

        List<Payment> result = service.getAllPayments();

        assertEquals(2, result.size());
        assertTrue(result.contains(p1));
        assertTrue(result.contains(p2));
        verify(repository).findAll();
    }
}
