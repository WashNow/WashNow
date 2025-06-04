package tqs.WashNow.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import tqs.WashNow.entities.CarwashBay;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class CarwashBayRespositoryIT {

    @Autowired
    private CarwashBayRepository carwashBayRepository;

    @Test
    void testSaveCarwashBay() {
        CarwashBay carwashBay = new CarwashBay();
        carwashBay.setIdentifiableName("Bay 1");
        carwashBay.setActive(true);
        carwashBay.setPricePerMinute(5.0);

        CarwashBay savedCarwashBay = carwashBayRepository.save(carwashBay);

        assertThat(savedCarwashBay).isNotNull();
        assertThat(savedCarwashBay.getId()).isNotNull();
        assertThat(savedCarwashBay.getIdentifiableName()).isEqualTo("Bay 1");
        assertThat(savedCarwashBay.isActive()).isTrue();
        assertThat(savedCarwashBay.getPricePerMinute()).isEqualTo(5.0);
    }

    @Test
    void testFindCarwashBayById() {
        CarwashBay carwashBay = new CarwashBay();
        carwashBay.setIdentifiableName("Bay 2");
        carwashBay.setActive(false);
        carwashBay.setPricePerMinute(10.0);
        carwashBay = carwashBayRepository.save(carwashBay);

        Optional<CarwashBay> retrievedCarwashBay = carwashBayRepository.findById(carwashBay.getId());

        assertThat(retrievedCarwashBay).isPresent();
        assertThat(retrievedCarwashBay.get().getIdentifiableName()).isEqualTo("Bay 2");
        assertThat(retrievedCarwashBay.get().isActive()).isFalse();
        assertThat(retrievedCarwashBay.get().getPricePerMinute()).isEqualTo(10.0);
    }

    @Test
    void testDeleteCarwashBay() {
        CarwashBay carwashBay = new CarwashBay();
        carwashBay.setIdentifiableName("Bay to Delete");
        carwashBay.setActive(true);
        carwashBay.setPricePerMinute(6.0);
        carwashBay = carwashBayRepository.save(carwashBay);

        carwashBayRepository.deleteById(carwashBay.getId());

        Optional<CarwashBay> deletedCarwashBay = carwashBayRepository.findById(carwashBay.getId());
        assertThat(deletedCarwashBay).isNotPresent();
    }
}