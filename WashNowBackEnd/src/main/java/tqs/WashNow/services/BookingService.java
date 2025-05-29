package tqs.WashNow.services;

import org.springframework.stereotype.Service;
import tqs.WashNow.repositories.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import tqs.WashNow.entities.Booking;
import tqs.WashNow.entities.BookingStatus;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookingService {

    private BookingRepository bookingRepository;

    @Autowired
    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    // POST
    public Booking createBooking(Long carwashBayId, Long userId, LocalDateTime startTime, LocalDateTime endTime) {
        Booking booking = null; // = bookingRepository.findBooking(carwashBayId, startTime, endTime);

        if (booking != null) {
            if (booking.getBookingStatus() == BookingStatus.AVAILABLE) {
                booking.setBookingStatus(BookingStatus.RESERVED);
                booking.setUserId(userId);
                return updateBookingById(booking.getId(), booking);
            }
            return null;
        }

        return bookingRepository.save(new Booking(carwashBayId, userId, startTime, endTime, BookingStatus.RESERVED));
    }

    // GET
    public Booking getBookingById(Long id) {
        return bookingRepository.findById(id).orElse(null);
    }

    // PUT
    public Booking updateBookingById(Long id, Booking booking) {
        if (bookingRepository.existsById(id)) {
            booking.setId(id);
            return bookingRepository.save(booking);
        }
        return null;
    }

    // DELETE
    public void deleteBookingById(Long id) {
        bookingRepository.deleteById(id);
    }

    // GET ALL
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }
}
