package tqs.WashNow.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import tqs.WashNow.entities.Payment;
import tqs.WashNow.services.PaymentService;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class PaymentControllerIT {

    @Mock
    private PaymentService paymentService;

    @InjectMocks
    private PaymentController paymentController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreatePayment() {
        Payment payment = new Payment();
        when(paymentService.createPayment(payment)).thenReturn(payment);

        ResponseEntity<Payment> response = paymentController.createPayment(payment);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        verify(paymentService, times(1)).createPayment(payment);
    }

    @Test
    void testGetAllPayments() {
        List<Payment> payments = Arrays.asList(new Payment(), new Payment());
        when(paymentService.getAllPayments()).thenReturn(payments);

        ResponseEntity<List<Payment>> response = paymentController.getAllPayments();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().size()).isEqualTo(2);
        verify(paymentService, times(1)).getAllPayments();
    }

    @Test
    void testGetPaymentById() {
        Long id = 1L;
        Payment payment = new Payment();
        when(paymentService.getPaymentById(id)).thenReturn(payment);

        ResponseEntity<Payment> response = paymentController.getPaymentById(id);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        verify(paymentService, times(1)).getPaymentById(id);
    }

    @Test
    void testUpdatePayment() {
        Long id = 1L;
        Payment updatedPayment = new Payment();
        when(paymentService.updatePaymentById(id, updatedPayment)).thenReturn(updatedPayment);

        ResponseEntity<Payment> response = paymentController.updatePayment(id, updatedPayment);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        verify(paymentService, times(1)).updatePaymentById(id, updatedPayment);
    }

    @Test
    void testDeletePayment() {
        Long id = 1L;
        Payment payment = new Payment();
        when(paymentService.getPaymentById(id)).thenReturn(payment);
        doNothing().when(paymentService).deletePaymentById(id);

        ResponseEntity<Void> response = paymentController.deletePayment(id);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        verify(paymentService, times(1)).deletePaymentById(id);
    }
}