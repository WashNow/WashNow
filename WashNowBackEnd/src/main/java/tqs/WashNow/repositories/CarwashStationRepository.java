package tqs.WashNow.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import tqs.WashNow.entities.CarwashStation;

public interface CarwashStationRepository extends JpaRepository<CarwashStation, Long> {
    
}
