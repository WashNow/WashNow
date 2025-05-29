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
import org.springframework.web.bind.annotation.RequestParam;

import tqs.WashNow.services.BookingService;
import tqs.WashNow.entities.Booking;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.time.LocalDateTime;
import java.util.List;

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
    public ResponseEntity<Booking> createBooking(@RequestParam Long carwashBayId, @RequestParam Long userId, 
                                        @RequestParam LocalDateTime startTime, @RequestParam LocalDateTime endTime) {
        Booking createdBooking = bookingService.createBooking(carwashBayId, userId, startTime, endTime);
        return ResponseEntity.ok(createdBooking);
        //return new ResponseEntity<>(createdBooking, HttpStatus.CREATED);
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
    

    
}