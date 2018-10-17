package fud.fud.Models;

import java.util.Date;

public class Event {
    public String eventName;
    public Date date;
    public String address;
    public String description;
    public String cuisineType;
    public double distanceToUser;
    public double price;

    // TODO: Implement User class eventually
    // private User user;

    public Event() {

    }

    public Event(Date date, String address, String description, String cuisineType, String eventName) {
        this.date = date;
        this.address = address;
        this.description = description;
        this.cuisineType = cuisineType;
        this.eventName = eventName;
    }

    public String getEventName() { return eventName; }
    public Date getDate() { return date; }
    public String getAddress() { return address; }
    public String getDescription() { return description; }
    public String getCuisineType() { return cuisineType; }
    public double getDistanceToUser() { return distanceToUser; }
    public double getPrice() { return price; }
}
