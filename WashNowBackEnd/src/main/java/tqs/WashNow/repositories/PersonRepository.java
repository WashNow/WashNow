package tqs.WashNow.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tqs.WashNow.entities.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {
    
}
