package tqs.WashNow.services;

import org.springframework.stereotype.Service;

import tqs.WashNow.repositories.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import tqs.WashNow.entities.Booking;
import java.util.List;


@Service
public class BookingService {

    private BookingRepository bookingRepository;

    @Autowired
    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    // POST
    public Booking createBooking(Booking booking) {
        return bookingRepository.save(booking);
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