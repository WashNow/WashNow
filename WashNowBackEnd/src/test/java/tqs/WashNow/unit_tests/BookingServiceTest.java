package tqs.WashNow.unit_tests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tqs.WashNow.entities.Booking;
import tqs.WashNow.repositories.BookingRepository;
import tqs.WashNow.services.BookingService;

@ExtendWith(MockitoExtension.class)
public class BookingServiceTest {

    @Mock
    private BookingRepository bookingRepository;

    @InjectMocks
    private BookingService bookingService;

    private Booking booking;
    private Long id, carwashBayId, userId;
    private LocalDateTime startTime, endTime;

    @BeforeEach
    void setUp() {
        booking = new Booking();
        id = 1L;
        booking.setId(id);
        carwashBayId = 1L;
        userId = 1L;
        startTime = LocalDateTime.of(2025, 5, 1, 8, 0);
        endTime = LocalDateTime.of(2025, 5, 1, 9, 0);
    }

    @Test
    void testCreateBooking() {
        Booking created = bookingService.createBooking(booking);
        System.out.println("Created booking: " + created);
        assertNotNull(created);
        assertEquals(id, created.getId());
        verify(bookingRepository, times(1)).save(booking);
    }

    @Test
    void testCreateBookingWhenExists() {
        bookingRepository.save(booking);
        assertTrue(bookingRepository.existsById(1L));

        when(bookingRepository.save(booking)).thenReturn(booking);

        Booking created = bookingService.createBooking(booking);
        assertNotNull(created);
        assertEquals(booking.getId(), created.getId());
        verify(bookingRepository, times(2)).save(booking);
    }

    @Test
    void testGetBookingById() {
        when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));

        Booking found = bookingService.getBookingById(1L);
        assertNotNull(found);
        assertEquals(booking.getId(), found.getId());
        verify(bookingRepository, times(1)).findById(1L);
    }

    @Test
    void testUpdateBookingByIdWhenExists() {
        when(bookingRepository.existsById(1L)).thenReturn(true);
        when(bookingRepository.save(booking)).thenReturn(booking);

        Booking updated = bookingService.updateBookingById(1L, booking);
        assertNotNull(updated);
        assertEquals(booking.getId(), updated.getId());
        verify(bookingRepository, times(1)).existsById(1L);
        verify(bookingRepository, times(1)).save(booking);
    }

    @Test
    void testUpdateBookingByIdWhenNotExists() {
        when(bookingRepository.existsById(1L)).thenReturn(false);

        Booking updated = bookingService.updateBookingById(1L, booking);
        assertNull(updated);
        verify(bookingRepository, times(1)).existsById(1L);
        verify(bookingRepository, never()).save(booking);
    }

    @Test
    void testDeleteBookingById() {
        doNothing().when(bookingRepository).deleteById(1L);
        bookingService.deleteBookingById(1L);
        verify(bookingRepository, times(1)).deleteById(1L);
    }

    @Test
    void testGetAllBookings() {
        List<Booking> allBookings = Arrays.asList(booking, new Booking());
        when(bookingRepository.findAll()).thenReturn(allBookings);

        List<Booking> result = bookingService.getAllBookings();
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(bookingRepository, times(1)).findAll();
    }
}
