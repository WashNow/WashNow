package tqs.WashNow.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import tqs.WashNow.entities.Booking;
import tqs.WashNow.entities.BookingStatus;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class BookingRepositoryIT {

    @Autowired
    private BookingRepository bookingRepository;

    @Test
    void testSaveBooking() {
        Booking booking = new Booking();
        booking.setCarwashBayId(1L);
        booking.setUserId(2L);
        booking.setStartTime(LocalDateTime.of(2023, 10, 1, 10, 0));
        booking.setEndTime(LocalDateTime.of(2023, 10, 1, 11, 0));
        booking.setBookingStatus(BookingStatus.RESERVED);

        Booking savedBooking = bookingRepository.save(booking);

        assertThat(savedBooking).isNotNull();
        assertThat(savedBooking.getId()).isNotNull();
        assertThat(savedBooking.getCarwashBayId()).isEqualTo(1L);
        assertThat(savedBooking.getUserId()).isEqualTo(2L);
        assertThat(savedBooking.getStartTime()).isEqualTo(LocalDateTime.of(2023, 10, 1, 10, 0));
        assertThat(savedBooking.getEndTime()).isEqualTo(LocalDateTime.of(2023, 10, 1, 11, 0));
        assertThat(savedBooking.getBookingStatus()).isEqualTo(BookingStatus.RESERVED);
    }

    @Test
    void testFindBookingById() {
        Booking booking = new Booking();
        booking.setCarwashBayId(1L);
        booking.setUserId(2L);
        booking.setStartTime(LocalDateTime.of(2023, 10, 2, 10, 0));
        booking.setEndTime(LocalDateTime.of(2023, 10, 2, 11, 0));
        booking.setBookingStatus(BookingStatus.IN_PROGRESS);
        booking = bookingRepository.save(booking);

        Optional<Booking> retrievedBooking = bookingRepository.findById(booking.getId());

        assertThat(retrievedBooking).isPresent();
        assertThat(retrievedBooking.get().getCarwashBayId()).isEqualTo(1L);
        assertThat(retrievedBooking.get().getUserId()).isEqualTo(2L);
        assertThat(retrievedBooking.get().getStartTime()).isEqualTo(LocalDateTime.of(2023, 10, 2, 10, 0));
        assertThat(retrievedBooking.get().getEndTime()).isEqualTo(LocalDateTime.of(2023, 10, 2, 11, 0));
        assertThat(retrievedBooking.get().getBookingStatus()).isEqualTo(BookingStatus.IN_PROGRESS);
    }

    @Test
    void testDeleteBooking() {
        Booking booking = new Booking();
        booking.setCarwashBayId(1L);
        booking.setUserId(2L);
        booking.setStartTime(LocalDateTime.of(2023, 10, 3, 10, 0));
        booking.setEndTime(LocalDateTime.of(2023, 10, 3, 11, 0));
        booking.setBookingStatus(BookingStatus.CANCELED);
        booking = bookingRepository.save(booking);

        bookingRepository.deleteById(booking.getId());

        Optional<Booking> deletedBooking = bookingRepository.findById(booking.getId());
        assertThat(deletedBooking).isNotPresent();
    }
}