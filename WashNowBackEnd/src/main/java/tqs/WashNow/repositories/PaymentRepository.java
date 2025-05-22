package tqs.WashNow.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import tqs.WashNow.entities.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    
}
