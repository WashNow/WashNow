package tqs.WashNow.entities;

import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "bay_id")
    private CarwashBay carwashBay;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private Person user;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @Enumerated(EnumType.STRING)
    private BookingStatus bookingStatus;

    @OneToOne(
        mappedBy = "booking", 
        cascade = CascadeType.ALL, // In order to auto-delete WashSession if Booking is deleted
        orphanRemoval = true
    )
    private WashSession washSession;

    // Construtores
    public Booking() {}

    public Booking(CarwashBay carwashBay, Person user, LocalDateTime startTime, LocalDateTime endTime,
            BookingStatus bookingStatus) {
        this.carwashBay = carwashBay;
        this.user = user;
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

    public CarwashBay getCarwashBay() {
        return carwashBay;
    }

    public void setCarwashBay(CarwashBay carwashBay) {
        this.carwashBay = carwashBay;
    }

    public Person getuser() {
        return user;
    }

    public void setuser(Person user) {
        this.user = user;
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