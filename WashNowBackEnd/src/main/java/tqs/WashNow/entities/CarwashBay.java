package tqs.WashNow.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class CarwashBay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "carwash_station_id")
    private CarwashStation carwashStation;

    private String identifiableName;
    private boolean isActive;
    private double pricePerMinute;  // não está a ser usado, ainda tenho dever com o prof se pode ficar só os preços no enum
    private int pressureBar;

    // Construtores
    public CarwashBay() {}

    public CarwashBay(CarwashStation carwashStation, String identifiableName, boolean isActive,
            int pressureBar) {
        this.carwashStation = carwashStation;
        this.identifiableName = identifiableName;
        this.isActive = isActive;
        // this.pricePerMinute = pricePerMinute;
        this.pressureBar = pressureBar;
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
    
    public int getPressureBar() {
        return pressureBar;
    }

    public void setPressureBar(int pressureBar) {
        this.pressureBar = pressureBar;
    }
}