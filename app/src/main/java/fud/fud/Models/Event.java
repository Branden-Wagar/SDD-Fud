package fud.fud.Models;

import java.util.Date;

public class Event {
    public Date endDate;        /* Includes time */
    public String address;      /* Longitude and latitude might be more helpful here */
    public float longitude;
    public float latitude;
    public String name;
    public String description;
    public String cuisineType;
    public double distanceToUser;   /* Will be updated periodically with a listener */
    public double price;
    public boolean isSponsored;     /* True if the event is sponsored, false otherwise */

    // TODO: Implement User class eventually
    // private User user;

    public Event(Date date, String address, String description, String cuisineType) {
        this.date = date;
        this.address = address;
        this.description = description;
        this.cuisineType = cuisineType;
    }
}
