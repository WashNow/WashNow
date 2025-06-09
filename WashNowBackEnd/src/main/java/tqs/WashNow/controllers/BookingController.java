package tqs.WashNow.controllers;

import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import tqs.WashNow.services.BookingService;
import tqs.WashNow.entities.Booking;
import tqs.WashNow.entities.BookingStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import java.util.List;

/**
 * REST controller that provides endpoints for managing booking operations for our application.
 * It includes operations for creating, retrieving, updating, and deleting bookings.
 * 
 * Endpoints:
 * - POST /api/bookings: Create a new booking.
 * - GET /api/bookings: Retrieve all bookings.
 * - GET /api/bookings/{id}: Retrieve a specific booking by its ID.
 * - PUT /api/bookings/{id}: Update a specific booking by its ID.
 * - DELETE /api/bookings/{id}: Delete a specific booking by its ID.
 * 
 * Dependencies:
 * - BookingService: A service layer dependency for handling booking-related business logic.
 * 
 * Response Codes:
 * - 201: Booking created successfully.
 * - 200: Request processed successfully (for example retrieving or updating bookings).
 * - 204: Booking deleted successfully.
 * - 400: Invalid request data.
 * - 404: Booking not found.
 * - 500: Internal server error.
 * 
 * @see BookingService
 * @see Booking
 */
@RestController
@RequestMapping("/api/bookings")
@Tag(name = "Booking", description = "Booking API")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    @Operation(summary = "Create a new booking")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Booking created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid request data")
    })
    public ResponseEntity<Booking> createBooking(@RequestBody Booking booking) {
        Booking createdBooking = bookingService.createBooking(booking);
        bookingService.updateBookingStatuses();
        return new ResponseEntity<>(createdBooking, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Get all bookings")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Bookings retrieved successfully"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<Booking>> getAllBookings() {
        List<Booking> bookings = bookingService.getAllBookings();
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a booking by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Booking retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "Booking not found")
    })
    public ResponseEntity<Booking> getBookingById(@PathVariable Long id) {
        Booking booking = bookingService.getBookingById(id);
        if (booking != null) {
            bookingService.updateBookingStatuses();
            return new ResponseEntity<>(booking, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a booking by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Booking updated successfully"),
        @ApiResponse(responseCode = "404", description = "Booking not found")
    })
    public ResponseEntity<Booking> updateBooking(@PathVariable Long id, @RequestBody Booking booking) {
        Booking updatedBooking = bookingService.updateBookingById(id, booking);
        bookingService.updateBookingStatuses();
        if (updatedBooking != null) {
            return new ResponseEntity<>(updatedBooking, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a booking by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Booking deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Booking not found")
    })
    public ResponseEntity<Void> deleteBooking(@PathVariable Long id) {
        if (bookingService.getBookingById(id) != null) {
            bookingService.deleteBookingById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<Void> cancelBooking(@PathVariable Long id) {
        Booking booking = bookingService.getBookingById(id);
    
        if (booking != null) {
            if (booking.getBookingStatus() == BookingStatus.RESERVED) {
                booking.setBookingStatus(BookingStatus.CANCELED);
                bookingService.updateBookingById(id, booking);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
}