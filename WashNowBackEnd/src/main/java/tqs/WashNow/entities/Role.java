package tqs.WashNow.entities;

public enum Role {
    DRIVER, // can interact normally with the site: relating booking and own personal info 
    OWNER,  // can do all the driver can plus add, remove, and edit carwash stations
    ADMIN   // to garantee the Separation of concerns and the Least-privilege principle
}