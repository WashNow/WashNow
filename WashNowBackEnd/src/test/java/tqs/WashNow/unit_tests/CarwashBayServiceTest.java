package tqs.WashNow.unit_tests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
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
    private CarwashBayRepository repository;

    @InjectMocks
    private CarwashBayService service;

    private CarwashStation station;

    @BeforeEach
    void setup() {
        station = new CarwashStation();
        station.setId(1L);
        station.setName("Estação A");
    }

    @Test
    void whenCreate_thenRepositorySaveAndReturn() {
        CarwashBay bay = new CarwashBay(null, station, "Bay-1", true, 1.5);
        CarwashBay saved = new CarwashBay(10L, station, "Bay-1", true, 1.5);

        when(repository.save(bay)).thenReturn(saved);

        CarwashBay result = service.createCarwashBay(bay);

        assertNotNull(result);
        assertEquals(10L, result.getId());
        verify(repository, times(1)).save(bay);
    }

    @Test
    void whenGetByIdExists_thenReturnBay() {
        CarwashBay bay = new CarwashBay(5L, station, "Bay-5", true, 2.0);
        when(repository.findById(5L)).thenReturn(Optional.of(bay));

        CarwashBay result = service.getCarwashBayById(5L);

        assertNotNull(result);
        assertEquals("Bay-5", result.getIdentifiableName());
        verify(repository).findById(5L);
    }

    @Test
    void whenGetByIdNotExists_thenReturnNull() {
        when(repository.findById(42L)).thenReturn(Optional.empty());

        CarwashBay result = service.getCarwashBayById(42L);

        assertNull(result);
        verify(repository).findById(42L);
    }

    @Test
    void whenUpdateExists_thenSaveWithIdAndReturn() {
        Long idToUpdate = 7L;
        CarwashBay incoming = new CarwashBay(null, station, "Bay-X", false, 3.0);
        CarwashBay updated = new CarwashBay(7L, station, "Bay-X", false, 3.0);

        when(repository.existsById(idToUpdate)).thenReturn(true);
        when(repository.save(any(CarwashBay.class))).thenReturn(updated);

        CarwashBay result = service.updateCarwashBayById(idToUpdate, incoming);

        assertNotNull(result);
        assertEquals(idToUpdate, result.getId());
        assertFalse(result.isActive());

        // Verifica que o ID foi setado antes do save
        ArgumentCaptor<CarwashBay> captor = ArgumentCaptor.forClass(CarwashBay.class);
        verify(repository).save(captor.capture());
        assertEquals(idToUpdate, captor.getValue().getId());
    }

    @Test
    void whenUpdateNotExists_thenReturnNullAndDontSave() {
        Long idToUpdate = 99L;
        CarwashBay incoming = new CarwashBay(null, station, "Bay-Y", true, 2.5);

        when(repository.existsById(idToUpdate)).thenReturn(false);

        CarwashBay result = service.updateCarwashBayById(idToUpdate, incoming);

        assertNull(result);
        verify(repository, never()).save(any());
    }

    @Test
    void whenDelete_thenRepositoryDeleteById() {
        service.deleteCarwashBayById(13L);
        verify(repository, times(1)).deleteById(13L);
    }

    @Test
    void whenGetAll_thenReturnList() {
        CarwashBay b1 = new CarwashBay(1L, station, "B1", true, 1.0);
        CarwashBay b2 = new CarwashBay(2L, station, "B2", false, 1.2);
        List<CarwashBay> list = Arrays.asList(b1, b2);

        when(repository.findAll()).thenReturn(list);

        List<CarwashBay> result = service.getAllCarwashBays();

        assertEquals(2, result.size());
        assertTrue(result.contains(b1));
        assertTrue(result.contains(b2));
        verify(repository).findAll();
    }

}
