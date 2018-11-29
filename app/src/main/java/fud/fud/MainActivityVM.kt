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
import fud.fud.Models.LocationManagerLocal
import kotlin.math.roundToInt

class MainActivityVM( ct : Context) : BaseObservable() {

    var NewEventFilter = ObservableField<String>("New Events")
    var FreeFoodFilter = ObservableField<String>("Free Only")
    var EventsListAdapter = ObservableField<ArrayAdapter<String>>()
    var FoodTypeFilters = ObservableField<ArrayAdapter<String>>(ArrayAdapter<String>(ct, android.R.layout.simple_list_item_1, ct.resources.getStringArray(R.array.food_tags)))
    var eventString = arrayListOf<String>()
    var events = arrayListOf<Event>()
    var parentContext = ct

    var locManager : LocationManagerLocal = LocationManagerLocal.instance
    val locationManager = parentContext.getSystemService(Activity.LOCATION_SERVICE) as LocationManager


    init{
        if (locationManager.isLocationEnabled && checkSelfPermission(parentContext, android.Manifest.permission.ACCESS_FINE_LOCATION) == PERMISSION_GRANTED){
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5, 5.1.toFloat(), locManager, null)
        }
    }
    private var maxEventPrice : Double = 0.0

    private var _freeFoodOnly = false

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

    fun getEvent( ct:Context,position: Int){
        val event = events.get(position)
        val intent = Intent(ct, EventDetails::class.java)
        intent.putExtra("Event",event)
        ct.startActivity(intent)
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

    fun onclickCreateEvent(){

    }


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

        var location : Location? = getCurrLocation()

        //get the information for each of the events
        dbManager.allEvents.addOnCompleteListener { task ->
            if (task.isSuccessful()) {
                var temp = task.getResult()
                temp!!.forEach {
                    // foreach document we get from allEvents convert it to an Event
                    val t = it.toObject(Event::class.java)
                    eventsL.add(t)
                    //eventString.add(t.toString()) // then put the string rep of the object in our events
                    if (t.price > maxEventPrice){
                        maxEventPrice = t.price
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
    private fun calculateDistance(l1 : Location?, l2 : Location?) : Double{

        if (l1 == null || l2 == null){
            return 0.0
        }
        var a = Math.abs(l1.latitude - l2.latitude)
        var b = Math.abs(l1.longitude - l2.longitude)
        // a^2 + b^2 = c^2
        var distance = Math.pow(a, 2.0) + Math.pow(b, 2.0)
        distance = Math.sqrt(distance)
        return distance
    }


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
    private fun filterPrice(lim : Double){
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
                    //eventString.add(t.toString()) // then put the string rep of the object in our events
                    if (t.price <= lim) {
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


