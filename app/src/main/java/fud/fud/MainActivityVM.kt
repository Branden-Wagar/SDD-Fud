package fud.fud

import android.app.Activity
import android.arch.lifecycle.LiveData
import android.content.Context
import android.content.Intent
import android.content.Context.LOCATION_SERVICE
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.databinding.BaseObservable
import android.databinding.Bindable
import android.databinding.Observable
import android.databinding.ObservableField
import android.location.Location
import android.location.LocationManager
import android.support.v4.content.ContextCompat.checkSelfPermission
import android.support.v4.content.ContextCompat.getSystemService
import android.support.v4.content.ContextCompat.startActivity
import android.widget.ArrayAdapter
import android.widget.ListView
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.TaskCompletionSource
import com.google.firebase.firestore.FirebaseFirestore
import fud.fud.Database.DatabaseManager
import fud.fud.Models.Event
import fud.fud.Models.EventLocation
import fud.fud.Models.LocationManagerLocal
import kotlin.math.roundToInt

class MainActivityVM( ct : Context, foodTagOptions : List<String>) : BaseObservable() {

    var FoodTagFilter = ObservableField<String>("Selected Tag Only")
    var FreeFoodFilter = ObservableField<String>("Free Only")
    var EventsListAdapter = ObservableField<ArrayAdapter<String>>()
    var FoodTypeFilters = ObservableField<ArrayAdapter<String>>(ArrayAdapter<String>(ct, android.R.layout.simple_list_item_1, ct.resources.getStringArray(R.array.food_tags)))
    var eventString = arrayListOf<String>()
    var events = arrayListOf<Event>()
    var parentContext = ct
    var foodTagIndex : ObservableField<Int> = ObservableField(0)

    var locManager: LocationManagerLocal = LocationManagerLocal.instance
    val locationManager = parentContext.getSystemService(Activity.LOCATION_SERVICE) as LocationManager

    private var _foodTagOptions = foodTagOptions
    private var maxEventPrice : Double = 0.0
    private var maxEventDistance : Double = 0.0
    private var _freeFoodOnly = false



    init{
        if (locationManager.isLocationEnabled && checkSelfPermission(parentContext, android.Manifest.permission.ACCESS_FINE_LOCATION) == PERMISSION_GRANTED){
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5, 5.1.toFloat(), locManager, null)
        }
    }



    @Bindable
    fun getFreeEventOnly() : Boolean{
        return _freeFoodOnly
    }
    fun setFreeEventOnly(input : Boolean){
        if (input != _freeFoodOnly){
            _freeFoodOnly = input
            if (input){
                filterPrice(0.0)
            }
            else{
                UpdateEventsList()
            }
        }
        notifyPropertyChanged(BR.freeEventOnly)
    }


    private var _priceLimit : Int = 0
    @Bindable
    fun getPriceLimit() : Int{
        return _priceLimit
    }
    fun setPriceLimit(value: Int) {
        if (value != _priceLimit){
            _priceLimit = value
            // our slider acts as a percentage value of all prices
            var x = _priceLimit / 100.0
            var k = x * maxEventPrice
            filterPrice(k)
        }
        notifyPropertyChanged(BR.priceLimit)
    }

    private var _distanceLimit : Int = 0
    @Bindable
    fun getDistanceLimit() : Int{
        return _distanceLimit
    }
    fun setDistanceLimit(value: Int){
        if (value != _distanceLimit){
            _distanceLimit = value
            var x = _distanceLimit / 100.0
            var k = x * maxEventDistance
            filterDistance(k)
        }
        notifyPropertyChanged(BR.distanceLimit)
    }


    private var _foodTagOnly : Boolean = false
    @Bindable
    fun getFoodTagOnly() : Boolean{
        return _foodTagOnly
    }
    fun setFoodTagOnly(value : Boolean){
        if (value != _foodTagOnly){
            _foodTagOnly = value
            if (value){
                filterTag()
            }
            else{
                UpdateEventsList()
            }
        }
        notifyPropertyChanged(BR.foodTagOnly)
    }


    //get the event clicked on from the ListView
    fun getEvent( ct:Context,position: Int){
        val event = events.get(position)
        //start the eventDetails activity and pass on the event
        val intent = Intent(ct, EventDetails::class.java)
        intent.putExtra("Event",event)
        ct.startActivity(intent)
    }

    fun onclickCreateEvent(){

    }


    /*
        Updates the event list with all events we can pull from the DB
     */
    fun UpdateEventsList() {
        var dbInstance = FirebaseFirestore.getInstance()
        var dbManager = DatabaseManager(dbInstance)
        //set up arrayLists for holding the information for the ListView

        events.clear()
        eventString.clear()
        eventString = ArrayList<String>()
        var eventsL = ArrayList<Event>()
        var localEvents = ArrayList<String>()
        maxEventPrice = 0.0
        maxEventDistance = 0.0
        var location : Location? = getCurrLocation()

        //get the information for each of the events
        dbManager.allEvents.addOnCompleteListener { task ->
            if (task.isSuccessful()) {
                var temp = task.getResult()
                temp!!.forEach {
                    // foreach document we get from allEvents convert it to an Event
                    val t = it.toObject(Event::class.java)
                    t.distanceToUser = calculateDistance(location, t.location)
                    eventsL.add(t)
                    //eventString.add(t.toString()) // then put the string rep of the object in our events
                    if (t.price > maxEventPrice){
                        maxEventPrice = t.price
                    }
                    if (t.distanceToUser > maxEventDistance){
                        maxEventDistance = t.distanceToUser
                    }
                    localEvents.add(t.toString()) // then put the string rep of the object in our events
                    EventsListAdapter.notifyChange()
                }

            }
        }
        eventString = localEvents
        events = eventsL
        EventsListAdapter.set(ArrayAdapter(parentContext, android.R.layout.simple_list_item_1, localEvents))



    }

    /*
        Haversine formula modified from
        https://stackoverflow.com/questions/27928/calculate-distance-between-two-latitude-longitude-points-haversine-formula
     */
    private fun calculateDistance(l1 : Location?, l2 : EventLocation?) : Double{

        if (l1 == null || l2 == null){
            return 0.0
        }


        var R = 6371 // Radius of the earth in km
        var dLat = deg2rad(l2.latitude-l1.latitude)  // deg2rad below
        var dLon = deg2rad(l2.longitude-l1.longitude)
        var a =
                Math.sin(dLat/2) * Math.sin(dLat/2) +
                        Math.cos(deg2rad(l1.latitude)) * Math.cos(deg2rad(l2.latitude)) *
                        Math.sin(dLon/2) * Math.sin(dLon/2)
        ;
        var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a))
        var d = R * c // Distance in km
        return d
    }

    /*
        Haversine formula helper, modified from
        https://stackoverflow.com/questions/27928/calculate-distance-between-two-latitude-longitude-points-haversine-formula
     */
    private fun deg2rad(deg : Double) : Double {
        return deg * (Math.PI/180)
    }

    /*
        Uses the best location provider available to get the device's current location
        if it cannot determine a location it will return null
     */
    private fun getCurrLocation() : Location?{
        return if (locationManager.isLocationEnabled && checkSelfPermission(parentContext, android.Manifest.permission.ACCESS_FINE_LOCATION) == PERMISSION_GRANTED){
            val providers = locationManager.getProviders(true)
            var bestLocation: Location? = null
            for (provider in providers) {
                val l = locationManager.getLastKnownLocation(provider) ?: continue
                if (bestLocation == null || l.accuracy < bestLocation.accuracy) {
                    // Found best last known location: %s", l);
                    bestLocation = l
                }
            }
            bestLocation
        }
        else{
            null
        }
    }


    /*
        Populates the events lists with events under the specified price (inclusive)
     */
    private fun filterPrice(lim : Double){
        filterBy({t -> t.price <= lim})
    }


    /*
        Populates the events lists with events within the specified distance (inclusive)
     */
    private fun filterDistance(lim : Double){
        filterBy({t -> t.distanceToUser <= lim})
    }


    /*
        Populates the events lists with foods that have the selected tag
     */
    private fun filterTag() {
        // we need to retrieve the requested foodTag from the UI
        var cuisineType = _foodTagOptions[foodTagIndex.get()!!]
        filterBy({t -> t.cuisineType == cuisineType})
    }



    /*
        Generic filterBy method accepts a function that takes an Event and returns a boolean.
        Then populates the eventsLists with only events that satisfy that function
     */
    private fun filterBy(comp : (Event) -> Boolean){
        var dbInstance = FirebaseFirestore.getInstance()
        var dbManager = DatabaseManager(dbInstance)
        events.clear()
        //eventString.clear()
        var eventLocal = ArrayList<Event>()
        eventString = ArrayList<String>()
        var eventStringLocal = ArrayList<String>()
        dbManager.allEvents.addOnCompleteListener { task ->
            if (task.isSuccessful()) {
                var temp = task.getResult()
                temp!!.forEach {
                    // foreach document we get from allEvents convert it to an Event
                    val t = it.toObject(Event::class.java)
                    t.distanceToUser = calculateDistance(getCurrLocation(), t.location)
                    //eventString.add(t.toString()) // then put the string rep of the object in our events
                    if (comp(t)) {
                        eventLocal.add(t)
                        eventStringLocal.add(t.toString()) // then put the string rep of the object in our events
                        EventsListAdapter.notifyChange()
                    }

                }
            }
            events = eventLocal
            eventString = eventStringLocal
            EventsListAdapter.set(ArrayAdapter(parentContext, android.R.layout.simple_list_item_1, eventStringLocal))
        }
    }


}


