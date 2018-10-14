package fud.fud.Models;

import java.util.Date;

public class Event {
    public Date date;
    public String address;
    public String description;
    public String cuisineType;
    public double distanceToUser;
    public double price;

    // TODO: Implement User class eventually
    // private User user;

    public Event(Date date, String address, String description, String cuisineType) {
        this.date = date;
        this.address = address;
        this.description = description;
        this.cuisineType = cuisineType;
    }
}
