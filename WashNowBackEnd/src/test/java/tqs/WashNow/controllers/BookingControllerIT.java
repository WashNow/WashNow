package tqs.WashNow.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import tqs.WashNow.entities.Booking;
import tqs.WashNow.services.BookingService;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class BookingControllerIT {

    @Mock
    private BookingService bookingService;

    @InjectMocks
    private BookingController bookingController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateBooking() {
        Booking booking = new Booking();
        when(bookingService.createBooking(booking)).thenReturn(booking);

        ResponseEntity<Booking> response = bookingController.createBooking(booking);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        verify(bookingService, times(1)).createBooking(booking);
    }

    @Test
    void testGetAllBookings() {
        List<Booking> bookings = Arrays.asList(new Booking(), new Booking());
        when(bookingService.getAllBookings()).thenReturn(bookings);

        ResponseEntity<List<Booking>> response = bookingController.getAllBookings();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().size()).isEqualTo(2);
        verify(bookingService, times(1)).getAllBookings();
    }

    @Test
    void testGetBookingById() {
        Long id = 1L;
        Booking booking = new Booking();
        when(bookingService.getBookingById(id)).thenReturn(booking);

        ResponseEntity<Booking> response = bookingController.getBookingById(id);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        verify(bookingService, times(1)).getBookingById(id);
    }

    @Test
    void testUpdateBooking() {
        Long id = 1L;
        Booking updatedBooking = new Booking();
        when(bookingService.updateBookingById(id, updatedBooking)).thenReturn(updatedBooking);

        ResponseEntity<Booking> response = bookingController.updateBooking(id, updatedBooking);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        verify(bookingService, times(1)).updateBookingById(id, updatedBooking);
    }

    @Test
    void testDeleteBooking() {
        Long id = 1L;
        Booking booking = new Booking();
        booking.setId(id);
        doNothing().when(bookingService).deleteBookingById(id);
        when(bookingService.getBookingById(id)).thenReturn(booking);

        ResponseEntity<Void> response = bookingController.deleteBooking(id);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        verify(bookingService, times(1)).deleteBookingById(id);
    }
}