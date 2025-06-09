package tqs.WashNow.controllers;

    import org.junit.jupiter.api.BeforeEach;
    import org.junit.jupiter.api.Test;
    import org.mockito.InjectMocks;
    import org.mockito.Mock;
    import org.mockito.MockitoAnnotations;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import tqs.WashNow.entities.CarwashBay;
    import tqs.WashNow.services.CarwashBayService;

    import java.util.Arrays;
    import java.util.List;

    import static org.assertj.core.api.Assertions.assertThat;
    import static org.mockito.Mockito.*;

    class CarwashBayControllerTest {

        @Mock
        private CarwashBayService carwashBayService;

        @InjectMocks
        private CarwashBayController carwashBayController;

        @BeforeEach
        void setUp() {
            MockitoAnnotations.openMocks(this);
        }

        @Test
        void testCreateCarwashBay() {
            CarwashBay carwashBay = new CarwashBay();
            when(carwashBayService.createCarwashBay(carwashBay)).thenReturn(carwashBay);

            ResponseEntity<CarwashBay> response = carwashBayController.createCarwashBay(carwashBay);

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
            assertThat(response.getBody()).isNotNull();
            verify(carwashBayService, times(1)).createCarwashBay(carwashBay);
        }

        @Test
        void testGetAllCarwashBays() {
            List<CarwashBay> carwashBays = Arrays.asList(new CarwashBay(), new CarwashBay());
            when(carwashBayService.getAllCarwashBays()).thenReturn(carwashBays);

            ResponseEntity<List<CarwashBay>> response = carwashBayController.getAllCarwashBays();

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(response.getBody()).isNotNull();
            assertThat(response.getBody().size()).isEqualTo(2);
            verify(carwashBayService, times(1)).getAllCarwashBays();
        }

        @Test
        void testGetCarwashBayById() {
            Long id = 1L;
            CarwashBay carwashBay = new CarwashBay();
            when(carwashBayService.getCarwashBayById(id)).thenReturn(carwashBay);

            ResponseEntity<CarwashBay> response = carwashBayController.getCarwashBayById(id);

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(response.getBody()).isNotNull();
            verify(carwashBayService, times(1)).getCarwashBayById(id);
        }

        @Test
        void testUpdateCarwashBay() {
            Long id = 1L;
            CarwashBay updatedCarwashBay = new CarwashBay();
            when(carwashBayService.updateCarwashBayById(id, updatedCarwashBay)).thenReturn(updatedCarwashBay);

            ResponseEntity<CarwashBay> response = carwashBayController.updateCarwashBay(id, updatedCarwashBay);

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(response.getBody()).isNotNull();
            verify(carwashBayService, times(1)).updateCarwashBayById(id, updatedCarwashBay);
        }

        @Test
        void testDeleteCarwashBay() {
            Long id = 1L;
            when(carwashBayService.getCarwashBayById(id)).thenReturn(new CarwashBay());

            ResponseEntity<Void> response = carwashBayController.deleteCarwashBay(id);

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
            verify(carwashBayService, times(1)).deleteCarwashBayById(id);
        }
    }