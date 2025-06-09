package tqs.WashNow.unit_tests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
import tqs.WashNow.entities.BookingStatus;
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
        startTime = LocalDateTime.of(2026, 5, 1, 8, 0);
        endTime = LocalDateTime.of(2026, 5, 1, 9, 0);
        booking.setStartTime(startTime);
        booking.setEndTime(endTime);
    }

    @Test
    void testCreateBooking() {
        when(bookingRepository.save(booking)).thenReturn(booking);
        Booking created = bookingService.createBooking(booking);
        assertNotNull(created);
        assertEquals(id, created.getId());
        verify(bookingRepository, times(1)).save(booking);
    }

    @Test
    void testCreateBookingWhenExists() {
        when(bookingRepository.existsById(1L)).thenReturn(true);
        bookingRepository.save(booking);
        assertTrue(bookingRepository.existsById(1L));

        Booking created = bookingService.createBooking(booking);
        assertNull(created);
        verify(bookingRepository, times(1)).save(booking);
    }

    @Test
    void testCreateBookingOldDate() {
        booking.setStartTime(LocalDateTime.of(2020, 1, 1, 10, 0));
        booking.setEndTime(LocalDateTime.of(2020, 1, 1, 11, 0));

        Booking created = bookingService.createBooking(booking);
        assertNull(created);
        verify(bookingRepository, never()).save(booking);
    }

    @Test
    void testCreateBookingWhenAlreadyExistsBookingThatDay() {
        booking.setId(null);
        booking.setUserId(42L);
        booking.setStartTime(LocalDateTime.now().plusHours(1));

        Booking existing = new Booking();
        existing.setId(100L);
        existing.setUserId(42L);
        existing.setStartTime(LocalDateTime.now().minusHours(5));
        existing.setBookingStatus(BookingStatus.RESERVED);

        when(bookingRepository.findAllByUserId(42L)).thenReturn(List.of(existing));
        Booking result = bookingService.createBooking(booking);

        assertNull(result);
        verify(bookingRepository, never()).save(any(Booking.class));
    }


    @Test
    void testLimitCreateBookings() {
        List<Booking> existingBookings = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Booking reservedBooking = new Booking();
            reservedBooking.setBookingStatus(BookingStatus.RESERVED);
            reservedBooking.setStartTime(LocalDateTime.now().minusDays(i));
            existingBookings.add(reservedBooking);
        }
        
        when(bookingRepository.findAllByUserId(userId)).thenReturn(existingBookings);

        Booking newBooking = new Booking();
        newBooking.setUserId(userId);
        newBooking.setStartTime(LocalDateTime.now().plusDays(1));
        newBooking.setEndTime(LocalDateTime.now().plusDays(1).plusHours(1));
        
        Booking created = bookingService.createBooking(newBooking);

        assertNull(created, "Limite de reservas por utilizador atingido!");
        
        verify(bookingRepository, never()).save(newBooking);
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
    void testGetBookingByIdWhenNotExists() {
        when(bookingRepository.findById(1L)).thenReturn(Optional.empty());
        Booking found = bookingService.getBookingById(1L);
        assertNull(found);
        verify(bookingRepository).findById(1L);
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
        assertTrue(result.contains(booking));
        verify(bookingRepository, times(1)).findAll();
    }
}
