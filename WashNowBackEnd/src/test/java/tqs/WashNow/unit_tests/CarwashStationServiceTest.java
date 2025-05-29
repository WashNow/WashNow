package tqs.WashNow.unit_tests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tqs.WashNow.entities.CarwashStation;
import tqs.WashNow.repositories.CarwashStationRepository;
import tqs.WashNow.services.CarwashStationService;

@ExtendWith(MockitoExtension.class)
class CarwashStationServiceTest {

    @Mock
    private CarwashStationRepository repository;

    @InjectMocks
    private CarwashStationService service;

    private CarwashStation station;

    @BeforeEach
    void setup() {
        station = new CarwashStation(
            null,
            "Station A",
            "123 Main St",
            40.123,
            -8.456,
            77L,
            150
        );
    }

    @Test
    void whenCreate_thenReturnSavedStation() {
        CarwashStation toSave = new CarwashStation(
            null, "New Station", "456 Side St", 41.0, -7.0, 88L, 200
        );
        CarwashStation saved = new CarwashStation(
            1L, "New Station", "456 Side St", 41.0, -7.0, 88L, 200
        );

        when(repository.save(toSave)).thenReturn(saved);

        CarwashStation result = service.createCarwashStation(toSave);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("New Station", result.getName());
        verify(repository, times(1)).save(toSave);
    }

    @Test
    void whenGetByIdExists_thenReturnStation() {
        CarwashStation existing = new CarwashStation(
            5L, "Exist Station", "100 Road", 39.0, -9.0, 99L, 120
        );
        when(repository.findById(5L)).thenReturn(Optional.of(existing));

        CarwashStation result = service.getCarwashStationById(5L);

        assertNotNull(result);
        assertEquals("Exist Station", result.getName());
        verify(repository).findById(5L);
    }

    @Test
    void whenGetByIdNotExists_thenReturnNull() {
        when(repository.findById(42L)).thenReturn(Optional.empty());

        CarwashStation result = service.getCarwashStationById(42L);

        assertNull(result);
        verify(repository).findById(42L);
    }

    @Test
    void whenUpdateExists_thenSaveAndReturnUpdated() {
        Long idToUpdate = 10L;
        CarwashStation incoming = new CarwashStation(
            null, "Updated Station", "999 New Ave", 38.5, -8.5, 55L, 180
        );
        CarwashStation updated = new CarwashStation(
            idToUpdate, "Updated Station", "999 New Ave", 38.5, -8.5, 55L, 180
        );

        when(repository.existsById(idToUpdate)).thenReturn(true);
        when(repository.save(any(CarwashStation.class))).thenReturn(updated);

        CarwashStation result = service.updateCarwashStationById(idToUpdate, incoming);

        assertNotNull(result);
        assertEquals(idToUpdate, result.getId());
        assertEquals("Updated Station", result.getName());

        ArgumentCaptor<CarwashStation> captor = ArgumentCaptor.forClass(CarwashStation.class);
        verify(repository).save(captor.capture());
        assertEquals(idToUpdate, captor.getValue().getId());
    }

    @Test
    void whenUpdateNotExists_thenReturnNullAndDoNotSave() {
        Long idToUpdate = 99L;
        CarwashStation incoming = new CarwashStation(
            null, "Nope Station", "Nowhere", 0.0, 0.0, 0L, 0
        );

        when(repository.existsById(idToUpdate)).thenReturn(false);

        CarwashStation result = service.updateCarwashStationById(idToUpdate, incoming);

        assertNull(result);
        verify(repository, never()).save(any());
    }

    @Test
    void whenDelete_thenRepositoryDeleteByIdCalled() {
        service.deleteCarwashStationById(7L);
        verify(repository, times(1)).deleteById(7L);
    }

    @Test
    void whenGetAll_thenReturnListOfStations() {
        CarwashStation s1 = new CarwashStation(1L, "S1", "Addr1", 10.0, 10.0, 11L, 100);
        CarwashStation s2 = new CarwashStation(2L, "S2", "Addr2", 20.0, 20.0, 22L, 110);
        List<CarwashStation> list = Arrays.asList(s1, s2);

        when(repository.findAll()).thenReturn(list);

        List<CarwashStation> result = service.getAllCarwashStations();

        assertEquals(2, result.size());
        assertTrue(result.contains(s1));
        assertTrue(result.contains(s2));
        verify(repository).findAll();
    }
}
