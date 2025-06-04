package tqs.WashNow.unit_tests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tqs.WashNow.entities.CarwashBay;
import tqs.WashNow.entities.CarwashStation;
import tqs.WashNow.repositories.CarwashBayRepository;
import tqs.WashNow.services.CarwashBayService;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(MockitoExtension.class)
class CarwashBayServiceTest {

    @Mock
    private CarwashBayRepository carwashBayRepository;

    @InjectMocks
    private CarwashBayService carwashBayService;

    private CarwashStation station;

    private CarwashBay bay;

    private Long id;

    @BeforeEach
    void setup() {
        station = new CarwashStation();
        id = 1L;
        station.setId(id);
        station.setName("Estação A");
        bay = new CarwashBay(id, station, "Bay-1", true, 1.5);
    }

    @Test
    void testCreateCarwashBay() {
        when(carwashBayRepository.save(bay)).thenReturn(bay);
        CarwashBay created = carwashBayService.createCarwashBay(bay);
        assertNotNull(created);
        assertEquals(id, created.getId());
        verify(carwashBayRepository, times(1)).save(bay);
    }

    @Test
    void testCreateCarwashBayWhenExists() {
        when(carwashBayRepository.existsById(1L)).thenReturn(true);
        carwashBayRepository.save(bay);
        assertTrue(carwashBayRepository.existsById(1L));

        CarwashBay created = carwashBayService.createCarwashBay(bay);
        assertNull(created);
        verify(carwashBayRepository, times(1)).save(bay);
    }

    @Test
    void testGetCarwashBayById() {
        when(carwashBayRepository.findById(1L)).thenReturn(Optional.of(bay));

        CarwashBay found = carwashBayService.getCarwashBayById(1L);
        assertNotNull(found);
        assertEquals(bay.getId(), found.getId());
        verify(carwashBayRepository, times(1)).findById(1L);
    }

    @Test
    void testGetCarwashBayByIdWhenNotExists() {
        when(carwashBayRepository.findById(1L)).thenReturn(Optional.empty());
        CarwashBay found = carwashBayService.getCarwashBayById(1L);
        assertNull(found);
        verify(carwashBayRepository).findById(1L);
    }   

    @Test
    void testUpdateCarwashBayByIdWhenExists() {
        when(carwashBayRepository.existsById(1L)).thenReturn(true);
        when(carwashBayRepository.save(bay)).thenReturn(bay);

        CarwashBay updated = carwashBayService.updateCarwashBayById(1L, bay);
        assertNotNull(updated);
        assertEquals(bay.getId(), updated.getId());
        verify(carwashBayRepository, times(1)).existsById(1L);
        verify(carwashBayRepository, times(1)).save(bay);
    }

    @Test
    void testUpdateCarwashBayByIdWhenNotExists() {
        when(carwashBayRepository.existsById(1L)).thenReturn(false);

        CarwashBay updated = carwashBayService.updateCarwashBayById(1L, bay);
        assertNull(updated);
        verify(carwashBayRepository, times(1)).existsById(1L);
        verify(carwashBayRepository, never()).save(bay);
    }

    @Test
    void testDeleteCarwashBayById() {
        carwashBayService.deleteCarwashBayById(1L);
        verify(carwashBayRepository, times(1)).deleteById(1L);
    }

    @Test
    void testGetAllCarwashBay() {
        List<CarwashBay> allBays = Arrays.asList(bay, new CarwashBay());
        when(carwashBayRepository.findAll()).thenReturn(allBays);

        List<CarwashBay> result = carwashBayService.getAllCarwashBays();
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(bay));
        verify(carwashBayRepository, times(1)).findAll();
    }
}
