package tqs.WashNow.services;

import org.springframework.stereotype.Service;

import tqs.WashNow.repositories.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import tqs.WashNow.entities.Booking;
import tqs.WashNow.entities.BookingStatus;

import java.util.List;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;


@Service
public class BookingService {

    private BookingRepository bookingRepository;

    @Autowired
    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    // POST
    public Booking createBooking(Booking booking) {
        if ((booking.getId() != null && bookingRepository.existsById(booking.getId())) ||
            (booking.getStartTime().isBefore(LocalDateTime.now()))) {
            return null;
        }

        int c = 0;
        for (Booking b : bookingRepository.findAllByUserId(booking.getUserId())) {
            if (Math.abs(ChronoUnit.HOURS.between(b.getStartTime(), booking.getStartTime())) < 24) return null;

            if (b.getBookingStatus().equals(BookingStatus.RESERVED)) c++;
    
            if (c >= 5) return null;
        }

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

    // Update booking statuses based on current time
    public void updateBookingStatuses() {
        List<Booking> bookings = bookingRepository.findAll();
        LocalDateTime now = LocalDateTime.now();
    
        for (Booking booking : bookings) {
            if (booking.getBookingStatus() == BookingStatus.RESERVED &&
                now.isAfter(booking.getStartTime()) && now.isBefore(booking.getEndTime())) {
                booking.setBookingStatus(BookingStatus.IN_PROGRESS);
                bookingRepository.save(booking);
            } else if (booking.getBookingStatus() == BookingStatus.IN_PROGRESS &&
                       now.isAfter(booking.getEndTime())) {
                booking.setBookingStatus(BookingStatus.WASHING_COMPLETED);
                bookingRepository.save(booking);
            }
        }
    }
    
    
}