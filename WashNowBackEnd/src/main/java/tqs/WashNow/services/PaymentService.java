package tqs.WashNow.services;

import org.springframework.stereotype.Service;

import tqs.WashNow.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import tqs.WashNow.entities.Payment;
import java.util.List;


/**
 * Service class responsible for handling payment-related operations in the WashNow application.
 * This service provides methods to create, read, update, and delete payment records,
 * acting as an intermediary layer between the controller and the repository.
 *
 * The service ensures that no duplicate payments are created based on ID and
 * handles the appropriate validation before performing database operations.
 */
@Service
public class PaymentService {

    private PaymentRepository PaymentRepository;

    @Autowired
    public PaymentService(PaymentRepository PaymentRepository) {
        this.PaymentRepository = PaymentRepository;
    }

    // POST
    public Payment createPayment(Payment payment) {
        if (payment.getId() != null && PaymentRepository.existsById(payment.getId())) return null;

        return PaymentRepository.save(payment);
    }

    // GET
    public Payment getPaymentById(Long id) {
        return PaymentRepository.findById(id).orElse(null);
    }

    // PUT
    public Payment updatePaymentById(Long id, Payment Payment) {
        if (PaymentRepository.existsById(id)) {
            Payment.setId(id);
            return PaymentRepository.save(Payment);
        }
        return null;
    }

    // DELETE
    public void deletePaymentById(Long id) {
        PaymentRepository.deleteById(id);
    }

    // GET ALL
    public List<Payment> getAllPayments() {
        return PaymentRepository.findAll();
    }
    
    
}
