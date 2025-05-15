package tqs.WashNow.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class CarwashBay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private CarwashStation carwashStation;

    private String identifiableName;
    private boolean isActive;
    private double pricePerMinute;

    // Construtores
    public CarwashBay() {}

    public CarwashBay(Long id, CarwashStation carwashStation, String identifiableName, boolean isActive,
            double pricePerMinute) {
        this.id = id;
        this.carwashStation = carwashStation;
        this.identifiableName = identifiableName;
        this.isActive = isActive;
        this.pricePerMinute = pricePerMinute;
    }

    // Getters e setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CarwashStation getCarwashStation() {
        return carwashStation;
    }

    public void setCarwashStation(CarwashStation carwashStation) {
        this.carwashStation = carwashStation;
    }

    public String getIdentifiableName() {
        return identifiableName;
    }

    public void setIdentifiableName(String identifiableName) {
        this.identifiableName = identifiableName;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public double getPricePerMinute() {
        return pricePerMinute;
    }

    public void setPricePerMinute(double pricePerMinute) {
        this.pricePerMinute = pricePerMinute;
    }
    
}