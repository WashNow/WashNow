package tqs.WashNow.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tqs.WashNow.entities.CarwashBay;

public interface CarwashBayRepository extends JpaRepository<CarwashBay, Long> {
    
}
