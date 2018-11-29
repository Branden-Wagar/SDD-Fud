package fud.fud.Models


import android.location.Location

import java.io.Serializable
import java.util.Date

class Event : Serializable {
    var eventName: String? = null
    var date: Date? = null
    var address: String? = null
    var description: String? = null
    var cuisineType: String? = null
    var distanceToUser: Double = 0.toDouble()
    var price: Double = 0.toDouble()
    var location: EventLocation? = null

    // TODO: Implement User class eventually
    // private User user;

    constructor() {
        this.eventName = ""
        this.date = Date()
        this.address = ""
        this.description = ""
        this.cuisineType = ""
        this.distanceToUser = 0.0
        this.price = 0.0
    }

    constructor(date: Date, address: String, description: String, cuisineType: String, eventName: String,
                price: Double, location: EventLocation) {
        this.date = date
        this.address = address
        this.description = description
        this.cuisineType = cuisineType
        this.eventName = eventName
        this.price = price
        this.location = location
        // Need to initialize distanceToUser
    }

    override fun toString(): String {
        val result = StringBuilder()
        val NL = System.getProperty("line.separator")

        //result.append(this.getClass().getName() + " Object {" + NL);
        result.append(" Name: $eventName$NL")
        //result.append(" Date: " + date.getTime() + NL);
        //result.append(" Address: " + address + NL );
        //result.append(" Description: " + description + NL);
        result.append(" Type: $cuisineType$NL")
        result.append(" Distance: $distanceToUser$NL")
        result.append(" Price: $price$NL")
        //result.append("}");

        return result.toString()
    }
}
