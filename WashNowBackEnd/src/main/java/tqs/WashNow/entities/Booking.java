package tqs.WashNow.entities;

import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long carwashBayId;
    private Long userId;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @Enumerated(EnumType.STRING)
    private BookingStatus bookingStatus;

    // Construtores
    public Booking() {}

    public Booking(Long id, Long carwashBayId, Long userId, LocalDateTime startTime, LocalDateTime endTime,
            BookingStatus bookingStatus) {
        this.id = id;
        this.carwashBayId = carwashBayId;
        this.userId = userId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.bookingStatus = bookingStatus;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCarwashBayId() {
        return carwashBayId;
    }

    public void setCarwashBayId(Long carwashBayId) {
        this.carwashBayId = carwashBayId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public BookingStatus getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(BookingStatus bookingStatus) {
        this.bookingStatus = bookingStatus;
    }


}