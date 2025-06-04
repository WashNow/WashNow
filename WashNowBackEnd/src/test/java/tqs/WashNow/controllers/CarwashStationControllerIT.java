package tqs.WashNow.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import tqs.WashNow.entities.CarwashStation;
import tqs.WashNow.services.CarwashStationService;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class CarwashStationControllerIT {

    @Mock
    private CarwashStationService carwashStationService;

    @InjectMocks
    private CarwashStationController carwashStationController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateCarwashStation() {
        CarwashStation carwashStation = new CarwashStation();
        when(carwashStationService.createCarwashStation(carwashStation)).thenReturn(carwashStation);

        ResponseEntity<CarwashStation> response = carwashStationController.createCarwashStation(carwashStation);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        verify(carwashStationService, times(1)).createCarwashStation(carwashStation);
    }

    @Test
    void testGetAllCarwashStations() {
        List<CarwashStation> carwashStations = Arrays.asList(new CarwashStation(), new CarwashStation());
        when(carwashStationService.getAllCarwashStations()).thenReturn(carwashStations);

        ResponseEntity<List<CarwashStation>> response = carwashStationController.getAllCarwashStations();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().size()).isEqualTo(2);
        verify(carwashStationService, times(1)).getAllCarwashStations();
    }

    @Test
    void testGetCarwashStationById() {
        Long id = 1L;
        CarwashStation carwashStation = new CarwashStation();
        when(carwashStationService.getCarwashStationById(id)).thenReturn(carwashStation);

        ResponseEntity<CarwashStation> response = carwashStationController.getCarwashStationById(id);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        verify(carwashStationService, times(1)).getCarwashStationById(id);
    }

    @Test
    void testUpdateCarwashStation() {
        Long id = 1L;
        CarwashStation updatedCarwashStation = new CarwashStation();
        when(carwashStationService.updateCarwashStationById(id, updatedCarwashStation)).thenReturn(updatedCarwashStation);

        ResponseEntity<CarwashStation> response = carwashStationController.updateCarwashStation(id, updatedCarwashStation);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        verify(carwashStationService, times(1)).updateCarwashStationById(id, updatedCarwashStation);
    }

    @Test
    void testDeleteCarwashStation() {
        Long id = 1L;
        when(carwashStationService.getCarwashStationById(id)).thenReturn(new CarwashStation());

        ResponseEntity<Void> response = carwashStationController.deleteCarwashStation(id);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        verify(carwashStationService, times(1)).deleteCarwashStationById(id);
    }
}