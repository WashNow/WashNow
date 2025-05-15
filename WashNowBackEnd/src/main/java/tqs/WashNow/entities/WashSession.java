package tqs.WashNow.entities;

import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class WashSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long bookingId;

    @Enumerated(EnumType.STRING)
    private WashProgram washProgram;

    private LocalDateTime startedAt;
    private LocalDateTime endedAt;
    private double totalCost;
    // Campo adicional futuro: private double litresUsed;

    // Construtores
    public WashSession() {}

    public WashSession(Long id, Long bookingId, WashProgram washProgram, LocalDateTime startedAt, LocalDateTime endedAt,
            double totalCost) {
        this.id = id;
        this.bookingId = bookingId;
        this.washProgram = washProgram;
        this.startedAt = startedAt;
        this.endedAt = endedAt;
        this.totalCost = totalCost;
    }

    // Getters e setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public WashProgram getWashProgram() {
        return washProgram;
    }

    public void setWashProgram(WashProgram washProgram) {
        this.washProgram = washProgram;
    }

    public LocalDateTime getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(LocalDateTime startedAt) {
        this.startedAt = startedAt;
    }

    public LocalDateTime getEndedAt() {
        return endedAt;
    }

    public void setEndedAt(LocalDateTime endedAt) {
        this.endedAt = endedAt;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }
    
}