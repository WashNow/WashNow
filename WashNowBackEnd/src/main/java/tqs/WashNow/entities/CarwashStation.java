package tqs.WashNow.entities;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class CarwashStation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String address;

    private double latitude;
    private double longitude;

    @ManyToOne(optional = false)
    @JoinColumn(name = "owner_id")
    private Person owner;

    @OneToMany(mappedBy = "station", cascade = CascadeType.ALL)
    private Set<CarwashBay> bays;

    // Construtores
    public CarwashStation() {}

    public CarwashStation(String name, String address, double latitude, double longitude, Person owner,
            Set<CarwashBay> bays) {
        this.name = name;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.owner = owner;
        this.bays = bays;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public Person getowner() {
        return owner;
    }

    public void setowner(Person owner) {
        this.owner = owner;
    }

    public Set<CarwashBay> getBays() { 
        return bays; 
    }
    public void setBays(Set<CarwashBay> bays) { 
        this.bays = bays; 
    }
}