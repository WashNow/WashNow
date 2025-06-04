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
    private PaymentRepository paymentRepository;

    @InjectMocks
    private PaymentService paymentService;

    private Payment payment;

    private Long id = 1L;

    @BeforeEach
    void setup() {
        payment = new Payment(
            id,
            50L,
            20.0,
            PaymentStatus.AUTHORIZED,
            LocalDateTime.of(2025, 5, 10, 12, 30)
        );
    }

    @Test
    void testCreateBooking() {
        when(paymentRepository.save(payment)).thenReturn(payment);
        Payment created = paymentService.createPayment(payment);
        assertNotNull(created);
        assertEquals(id, created.getId());
        verify(paymentRepository, times(1)).save(payment);
    }

    @Test
    void testCreateBookingWhenExists() {
        when(paymentRepository.existsById(1L)).thenReturn(true);
        paymentRepository.save(payment);
        assertTrue(paymentRepository.existsById(1L));

        Payment created = paymentService.createPayment(payment);
        assertNull(created);
        verify(paymentRepository, times(1)).save(payment);
    }

    @Test
    void testGetBookingById() {
        when(paymentRepository.findById(1L)).thenReturn(Optional.of(payment));

        Payment found = paymentService.getPaymentById(1L);
        assertNotNull(found);
        assertEquals(payment.getId(), found.getId());
        verify(paymentRepository, times(1)).findById(1L);
    }

    @Test
    void testGetBookingByIdWhenNotExists() {
        when(paymentRepository.findById(1L)).thenReturn(Optional.empty());
        Payment found = paymentService.getPaymentById(1L);
        assertNull(found);
        verify(paymentRepository).findById(1L);
    }    

    @Test
    void testUpdateBookingByIdWhenExists() {
        when(paymentRepository.existsById(1L)).thenReturn(true);
        when(paymentRepository.save(payment)).thenReturn(payment);

        Payment updated = paymentService.updatePaymentById(1L, payment);
        assertNotNull(updated);
        assertEquals(payment.getId(), updated.getId());
        verify(paymentRepository, times(1)).existsById(1L);
        verify(paymentRepository, times(1)).save(payment);
    }

    @Test
    void testUpdateBookingByIdWhenNotExists() {
        when(paymentRepository.existsById(1L)).thenReturn(false);

        Payment updated = paymentService.updatePaymentById(1L, payment);
        assertNull(updated);
        verify(paymentRepository, times(1)).existsById(1L);
        verify(paymentRepository, never()).save(payment);
    }

    @Test
    void testDeleteBookingById() {
        paymentService.deletePaymentById(1L);
        verify(paymentRepository, times(1)).deleteById(1L);
    }

    @Test
    void testGetAllBookings() {
        List<Payment> allBookings = Arrays.asList(payment, new Payment());
        when(paymentRepository.findAll()).thenReturn(allBookings);

        List<Payment> result = paymentService.getAllPayments();
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(payment));
        verify(paymentRepository, times(1)).findAll();
    }
}
