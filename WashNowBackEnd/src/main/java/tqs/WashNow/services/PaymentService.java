package tqs.WashNow.services;

import org.springframework.stereotype.Service;

import tqs.WashNow.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import tqs.WashNow.entities.Payment;
import java.util.List;


@Service
public class PaymentService {

    private PaymentRepository PaymentRepository;

    @Autowired
    public PaymentService(PaymentRepository PaymentRepository) {
        this.PaymentRepository = PaymentRepository;
    }

    // POST
    public Payment createPayment(Payment Payment) {
        if (PaymentRepository.existsById(Payment.getId())) return null;

        return PaymentRepository.save(Payment);
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
