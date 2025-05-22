package tqs.WashNow.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tqs.WashNow.entities.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {
}