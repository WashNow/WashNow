package tqs.WashNow.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tqs.WashNow.entities.Booking;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findAllByUserId(Long userId);
}