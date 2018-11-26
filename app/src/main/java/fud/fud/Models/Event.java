package fud.fud.Models;


import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.Date;

public class Event implements Serializable {
    private String eventName;
    private Date date;
    private String address;
    private String description;
    private String cuisineType;
    private double distanceToUser;
    private double price;

    // TODO: Implement User class eventually
    // private User user;

    public Event() {
        this.eventName = "";
        this.date = new Date();
        this.address = "";
        this.description = "";
        this.cuisineType = "";
        this.distanceToUser = 0.0;
        this.price = 0.0;
    }

    public Event(Date date, String address, String description, String cuisineType, String eventName,
                 double price) {
        this.date = date;
        this.address = address;
        this.description = description;
        this.cuisineType = cuisineType;
        this.eventName = eventName;
        this.price = price;

        // Need to initialize distanceToUser
    }

    public String getEventName() { return eventName; }
    public Date getDate() { return date; }
    public String getAddress() { return address; }
    public String getDescription() { return description; }
    public String getCuisineType() { return cuisineType; }
    public double getDistanceToUser() { return distanceToUser; }
    public double getPrice() { return price; }

    public void setEventName(String s) { eventName = s; }
    public void setDate(Date d) { date = d; }
    public void setAddress(String s) { address = s; }
    public void setDescription(String s) { description = s; }
    public void setCuisineType(String s) { cuisineType = s; }
    public void setDistanceToUser(double d) { distanceToUser = d; }
    public void setPrice(double d) { price = d; }

    @NonNull
    public String toString() {
        StringBuilder result = new StringBuilder();
        String NL = System.getProperty("line.separator");

        //result.append(this.getClass().getName() + " Object {" + NL);
        result.append(" Name: " + eventName + NL);
        //result.append(" Date: " + date.getTime() + NL);
        //result.append(" Address: " + address + NL );
        //result.append(" Description: " + description + NL);
        result.append(" Type: " + cuisineType + NL );
        result.append(" Distance: " + distanceToUser + NL);
        result.append(" Price: " + price + NL);
        //result.append("}");

        return result.toString();
    }
}
