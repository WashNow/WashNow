package tqs.WashNow.entities;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;

    private double balance;

    @OneToMany(mappedBy = "user")
    private Set<Booking> bookings;

    // Construtores
    public Person() {
        this.bookings = new HashSet<>();
    }

    public Person(String name, String email, Role role, double balance) {
        this.name = name;
        this.email = email;
        this.role = role;
        this.balance = balance;
        this.bookings = new HashSet<>();
    }

    // Getters e setters
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Set<Booking> getBookings() { 
        return bookings; 
    }
    public void setBookings(Set<Booking> bookings) { 
        this.bookings = bookings; 
    }
}