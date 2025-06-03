package tqs.WashNow.unit_tests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tqs.WashNow.entities.CarwashStation;
import tqs.WashNow.repositories.CarwashStationRepository;
import tqs.WashNow.services.CarwashStationService;

@ExtendWith(MockitoExtension.class)
class CarwashcarwashStationServiceTest {

    @Mock
    private CarwashStationRepository carwashStationRepository;

    @InjectMocks
    private CarwashStationService carwashStationService;

    private CarwashStation station;

    private Long id = 1L;

    @BeforeEach
    void setup() {
        station = new CarwashStation(
            id,
            "Station A",
            "123 Main St",
            40.123,
            -8.456,
            77L,
            150
        );
    }

    @Test
    void testCreatestation() {
        when(carwashStationRepository.save(station)).thenReturn(station);
        CarwashStation created = carwashStationService.createCarwashStation(station);
        assertNotNull(created);
        assertEquals(id, created.getId());
        verify(carwashStationRepository, times(1)).save(station);
    }

    @Test
    void testCreatestationWhenExists() {
        when(carwashStationRepository.existsById(1L)).thenReturn(true);
        carwashStationRepository.save(station);
        assertTrue(carwashStationRepository.existsById(1L));

        CarwashStation created = carwashStationService.createCarwashStation(station);
        assertNull(created);
        verify(carwashStationRepository, times(1)).save(station);
    }

    @Test
    void testGetstationById() {
        when(carwashStationRepository.findById(1L)).thenReturn(Optional.of(station));

        CarwashStation found = carwashStationService.getCarwashStationById(1L);
        assertNotNull(found);
        assertEquals(station.getId(), found.getId());
        verify(carwashStationRepository, times(1)).findById(1L);
    }

    @Test
    void testGetstationByIdWhenNotExists() {
        when(carwashStationRepository.findById(1L)).thenReturn(Optional.empty());
        CarwashStation found = carwashStationService.getCarwashStationById(1L);
        assertNull(found);
        verify(carwashStationRepository).findById(1L);
    }    

    @Test
    void testUpdatestationByIdWhenExists() {
        when(carwashStationRepository.existsById(1L)).thenReturn(true);
        when(carwashStationRepository.save(station)).thenReturn(station);

        CarwashStation updated = carwashStationService.updateCarwashStationById(1L, station);
        assertNotNull(updated);
        assertEquals(station.getId(), updated.getId());
        verify(carwashStationRepository, times(1)).existsById(1L);
        verify(carwashStationRepository, times(1)).save(station);
    }

    @Test
    void testUpdatestationByIdWhenNotExists() {
        when(carwashStationRepository.existsById(1L)).thenReturn(false);

        CarwashStation updated = carwashStationService.updateCarwashStationById(1L, station);
        assertNull(updated);
        verify(carwashStationRepository, times(1)).existsById(1L);
        verify(carwashStationRepository, never()).save(station);
    }

    @Test
    void testDeletestationById() {
        carwashStationService.deleteCarwashStationById(1L);
        verify(carwashStationRepository, times(1)).deleteById(1L);
    }

    @Test
    void testGetAllstations() {
        List<CarwashStation> allstations = Arrays.asList(station, new CarwashStation());
        when(carwashStationRepository.findAll()).thenReturn(allstations);

        List<CarwashStation> result = carwashStationService.getAllCarwashStations();
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(station));
        verify(carwashStationRepository, times(1)).findAll();
    }
}
