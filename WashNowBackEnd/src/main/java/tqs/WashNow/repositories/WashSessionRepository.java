package tqs.WashNow.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import tqs.WashNow.entities.WashSession;

public interface WashSessionRepository extends JpaRepository<WashSession, Long> {
    
}
