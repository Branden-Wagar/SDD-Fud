package fud.fud.Models;

import java.util.Date;

public class Event {
    private Date date;
    private String address;
    private String description;
    private String cuisineType;

    // TODO: Implement User class eventually
    // private User user;

    public Event(Date date, String address, String description, String cuisineType) {
        this.date = date;
        this.address = address;
        this.description = description;
        this.cuisineType = cuisineType;
    }
}
