package tqs.WashNow.repositories;

import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import tqs.WashNow.entities.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    

}