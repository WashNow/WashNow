package tqs.WashNow.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import tqs.WashNow.entities.CarwashStation;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class CarwashStationRepositoryIT {

    @Autowired
    private CarwashStationRepository carwashStationRepository;

    @Test
    void testSaveCarwashStation() {
        CarwashStation carwashStation = new CarwashStation();
        carwashStation.setName("Station 1");
        carwashStation.setAddress("123 Main Street");
        carwashStation.setLatitude(40.7128);
        carwashStation.setLongitude(-74.0060);
        carwashStation.setOwnerID(1L);
        carwashStation.setPressureBar(120);

        CarwashStation savedCarwashStation = carwashStationRepository.save(carwashStation);

        assertThat(savedCarwashStation).isNotNull();
        assertThat(savedCarwashStation.getId()).isNotNull();
        assertThat(savedCarwashStation.getName()).isEqualTo("Station 1");
        assertThat(savedCarwashStation.getAddress()).isEqualTo("123 Main Street");
        assertThat(savedCarwashStation.getLatitude()).isEqualTo(40.7128);
        assertThat(savedCarwashStation.getLongitude()).isEqualTo(-74.0060);
        assertThat(savedCarwashStation.getOwnerID()).isEqualTo(1L);
        assertThat(savedCarwashStation.getPressureBar()).isEqualTo(120);
    }

    @Test
    void testFindCarwashStationById() {
        CarwashStation carwashStation = new CarwashStation();
        carwashStation.setName("Station 2");
        carwashStation.setAddress("456 Elm Street");
        carwashStation.setLatitude(34.0522);
        carwashStation.setLongitude(-118.2437);
        carwashStation.setOwnerID(2L);
        carwashStation.setPressureBar(100);
        carwashStation = carwashStationRepository.save(carwashStation);

        Optional<CarwashStation> retrievedCarwashStation = carwashStationRepository.findById(carwashStation.getId());

        assertThat(retrievedCarwashStation).isPresent();
        assertThat(retrievedCarwashStation.get().getName()).isEqualTo("Station 2");
        assertThat(retrievedCarwashStation.get().getAddress()).isEqualTo("456 Elm Street");
        assertThat(retrievedCarwashStation.get().getLatitude()).isEqualTo(34.0522);
        assertThat(retrievedCarwashStation.get().getLongitude()).isEqualTo(-118.2437);
        assertThat(retrievedCarwashStation.get().getOwnerID()).isEqualTo(2L);
        assertThat(retrievedCarwashStation.get().getPressureBar()).isEqualTo(100);
    }

    @Test
    void testDeleteCarwashStation() {
        CarwashStation carwashStation = new CarwashStation();
        carwashStation.setName("Station to Delete");
        carwashStation.setAddress("202 Maple Street");
        carwashStation.setLatitude(41.8781);
        carwashStation.setLongitude(-87.6298);
        carwashStation.setOwnerID(5L);
        carwashStation.setPressureBar(80);
        carwashStation = carwashStationRepository.save(carwashStation);

        carwashStationRepository.deleteById(carwashStation.getId());

        Optional<CarwashStation> deletedCarwashStation = carwashStationRepository.findById(carwashStation.getId());
        assertThat(deletedCarwashStation).isNotPresent();
    }
}